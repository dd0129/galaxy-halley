package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by adima on 14-3-23.
 */
public class ReadyExecuter {
    private static final Logger logger = LoggerFactory.getLogger(ReadyExecuter.class);

    @Resource
    private InstanceDAO instDAO;

    public void execute(){
        try{
            logger.info("the ready thread starts");
            List<InstanceDO> list = instDAO.getInitInstanceList(Const.JOB_STATUS.JOB_INIT.getValue(),System.currentTimeMillis());
            for(InstanceDO inst : list){
                try{
                    boolean flag = this.updateTask(inst);
                    if(flag){
                        logger.info(inst.getInstanceId()+ "("+inst.getTaskName() + ") is ready");
                        this.instDAO.updateInstnaceStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_READY.getValue(), Const.JOB_STATUS.JOB_READY.getDesc());
                    }
                }catch(Exception e){
                    logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") update ready error",e);
                }
            }
        }finally{
            logger.info("the ready thread ends");
        }

    }

    private boolean updateTask(InstanceDO inst){
        if(inst.getIfPre() == CoreConst.TASK_NONEXISTS_PRE){
            return true;
        }else if(inst.getIfPre() == CoreConst.TASK_EXISTS_PRE){
            List<InstanceDO> list = this.instDAO.getRelaInstanceList(inst.getInstanceId());
            for(InstanceDO preInst: list){
                if(preInst.getStatus() == null){
                    logger.info(inst.getInstanceId()+ "("+inst.getTaskName() + ") not ready,pre job "
                            + preInst.getInstanceId() + "(" + preInst.getTaskName() + ")" + " does not have initialization ");
                    return false;
                }else if(preInst.getStatus().intValue() == Const.JOB_STATUS.JOB_SUCCESS.getValue().intValue()){
                    continue;
                }else {
                    logger.info(inst.getInstanceId()+"(" + inst.getTaskName() + ") not ready,pre job "
                            + preInst.getInstanceId() + "(" + preInst.getTaskName() + ") status is "+preInst.getStatus() );
                    return false;
                }
            }
            return true;
        }else{
            logger.error(inst.getInstanceId()+"(" + inst.getTaskName() + ") if_pre is illegal");
            return false;
        }
    }
}
