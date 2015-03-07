package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.ExceptionAlertDO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.external.DQCExecuterImpl;
import com.dianping.data.warehouse.external.ExternalExecuter;
import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.halley.domain.TaskReturnDO;
import com.dianping.data.warehouse.resource.ResourceManager2;
import com.dianping.data.warehouse.utils.DateFormatUtils;
import com.dianping.data.warehouse.utils.ExceptionAnalyze;
import com.dianping.data.warehouse.utils.ProcessUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Created by admin on 14-4-9.
 */

public class Task2 implements Runnable{
    private Logger logger = LoggerFactory.getLogger(Task2.class);

    private InstanceDAO instDAO;
    private InstanceDO inst;
    private ExceptionAnalyze exceptionAnalyze;

    public Task2(InstanceDAO instDAO, InstanceDO inst,ExceptionAnalyze exceptionAnalyze){
        this.instDAO = instDAO;
        this.inst = inst;
        this.exceptionAnalyze = exceptionAnalyze;
    }

    @Override
    public String toString(){
        return "Task"+this.inst.getInstanceId();
    }

    @Override
    public void run() {
        logger.info(inst.getInstanceId()+"("+inst.getTaskName()+") execute thread starts");
        try {
            Integer rtn=-1000;
            try{
                rtn = this.executeTask();
            }catch(Exception e){
                logger.error("execute process Error:",e);
            }
//            finally{
//                ResourceManager2.outQueue(inst);
//            }
            logger.warn(inst.getInstanceId()+"("+inst.getTaskName() +") rtn :"+rtn);
            //suspend任务的时候，直接返回，不需要修改数据库
            String currTime = DateFormatUtils.getFormatter().format(new Date());
            InstanceDO checkedInst = instDAO.getInstanceInfo(inst.getInstanceId());
            if(checkedInst.getStatus().intValue() == Const.JOB_STATUS.JOB_SUSPEND.getValue()){
                logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") retcode "+rtn+" is suspend");
                ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_SUSPEND.getValue());
                return;
            }else if(inst.getIfWait() == CoreConst.TASK_IF_WAIT && this.containCode(rtn,inst.getWaitCode())){
                logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") retcode "+rtn+" is wait");
                this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_WAIT.getValue(),
                        Const.JOB_STATUS.JOB_WAIT.getDesc(),currTime,rtn,null);
                ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_WAIT.getValue());
                return;
            }else if(!this.containCode(rtn, inst.getSuccessCode())){
                //解析日志
                rtn = this.getExceptionCode(inst);
                logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") retcode "+rtn+" is fail");
                this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_FAIL.getValue(),
                        Const.JOB_STATUS.JOB_FAIL.getDesc(),currTime,rtn,null);
                ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_FAIL.getValue());
                return;
            }else{
                logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+"record external log");
                ExternalExecuter executer = new DQCExecuterImpl();
                TaskReturnDO rtnObj = executer.execute(inst);
                this.recoredExternalLog(inst,rtnObj);
                return;
            }
        } catch (Exception e) {
            logger.error(inst.getInstanceId()+"("+inst.getTaskName()+") execute thread error", e);
        } finally {
            logger.info(inst.getInstanceId()+"("+inst.getTaskName()+") execute thread ends");
        }
    }

    public int getExceptionCode(InstanceDO inst)
    {
        File file = new File(StringUtils.join(new String[]{inst.getLogPath(), "snapshot"}, "."));
        FileInputStream is;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("log parse error :",e);
            return CoreConst.WORMHOLE_UNKNOWN_EXCEPTION;
        }
        logger.warn("log path at :"+file);
        if(inst.getType().intValue() == CoreConst.TASK_TYPE_LOAD){
            ExceptionAlertDO alert = exceptionAnalyze.analyze(is, CoreConst.TASK_LOAD_TYPE_DESC);
            if (alert != null){
                logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+")\n alert info :"+alert.getDescription()+"## alert id:" +alert.getId());
                return alert.getId();
            }
            return CoreConst.WORMHOLE_UNKNOWN_EXCEPTION;
        }else{
            ExceptionAlertDO alert = exceptionAnalyze.analyze(is, CoreConst.TASK_CALCULATE_TYPE_DESC);
            if (alert != null){
                logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+")\n alert info :"+alert.getDescription()+"## alert id:" +alert.getId());
                return alert.getId();
            }
            return CoreConst.CALCULATE_UNKNOWN_EXCEPTION;
        }

    }

    private Integer executeTask(){
        logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") job starts");
        if(inst.getType()== CoreConst.TASK_TYPE_LOAD){
            return ProcessUtils.executeWormholeCommand(inst);
        }else{
            return ProcessUtils.executeCommand(inst);
        }
//
//        else if(inst.getType()== CoreConst.TASK_TYPE_CALCULATE){
//        }else{
//            this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_INTERNAL_ERROR.getValue(),
//                    Const.JOB_STATUS.JOB_INTERNAL_ERROR.getDesc(),currTime,CoreConst.DEFAULT_TASK_JOBCODE,null);
//            logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") type "+inst.getType()+ "is illegal type");
//            throw new IllegalArgumentException(inst.getInstanceId() + "(" + inst.getTaskName() + ") type "+inst.getType()+ "is illegal type");
//        }
    }

    public boolean containCode(Integer code,String codes){
        if(StringUtils.isBlank(codes)){
            throw new NullPointerException("codes is null");
        }
        for (String tmp : codes.split(";")) {
            if (code == Integer.valueOf(tmp).intValue()) {
                return true;
            }
        }
        return false;
    }

    /*
    public void recoredInternalLog(InstanceDO inst, Integer rtn) {
        String currTime = DateFormatUtils.getFormatter().format(new Date());
        try{
            if(this.containCode(rtn,inst.getSuccessCode())){
                logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") is success");
                this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_SUCCESS.getValue(),
                        Const.JOB_STATUS.JOB_SUCCESS.getDesc(),currTime,rtn,null);
                return;
            }
            if (inst.getIfWait() == CoreConst.TASK_IF_WAIT) {
                if(this.containCode(rtn,inst.getWaitCode())){
                    logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") retcode "+rtn+" is wait");
                    this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_WAIT.getValue(),
                            Const.JOB_STATUS.JOB_WAIT.getDesc(),currTime,rtn,null);
                    return;
                }
            }
            logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") retcode "+rtn+" is fail");
            this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_FAIL.getValue(),
                    Const.JOB_STATUS.JOB_FAIL.getDesc(),currTime,rtn,null);

        }catch(Throwable e){
            logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") record log error",e);
            this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_FAIL.getValue(),
                    Const.JOB_STATUS.JOB_FAIL.getDesc(),currTime,rtn,null);
        }
    }
    */

    public void recoredExternalLog(InstanceDO inst, TaskReturnDO rtnObj) {
        String currTime = DateFormatUtils.getFormatter().format(new Date());
        int rtn = -1;
        try{
            if(rtnObj == null || rtnObj.getCode() == null){
                instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_SUCCESS.getValue(),
                        Const.JOB_STATUS.JOB_SUCCESS.getDesc(), currTime, -1,null);
                ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_SUCCESS.getValue());
                return;
            }

            rtn = rtnObj.getCode().intValue();
            String message = StringUtils.substring(rtnObj.getMessage(),0,300);

            Const.EXTERNAL_ERROR_CODES[] codes = Const.EXTERNAL_ERROR_CODES.values();
            for(Const.EXTERNAL_ERROR_CODES code : codes){
                if(code.getCode().intValue() == rtn){
                    instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_FAIL.getValue(),
                            Const.JOB_STATUS.JOB_FAIL.getDesc(), currTime,rtn,message);
                    ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_FAIL.getValue());
                    return;
                }
            }
            instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_SUCCESS.getValue(),
                    Const.JOB_STATUS.JOB_SUCCESS.getDesc(), currTime, rtn,message);
            ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_SUCCESS.getValue());
        }catch(Throwable e){
            logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") record external log error",e);
//            this.instDAO.updateInstEndStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_INIT.getValue(),
//                    Const.JOB_STATUS.JOB_INIT.getDesc(),currTime,rtn,null);
//            ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_SUCCESS.getValue());
        }
    }

}
