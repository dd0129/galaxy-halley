package com.dianping.data.warehouse.external;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.halley.domain.TaskReturnDO;
import com.dianping.data.warehouse.utils.DateUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-4-1.
 */

public class DQCExecuterImpl implements ExternalExecuter{
    private static Logger logger = LoggerFactory.getLogger(DQCExecuterImpl.class);

    public TaskReturnDO execute(InstanceDO inst) {
        try{
            ClassLoaderUtil.loadJarPath(CoreConst.EXTERNAL_CLASSPATH);
            Class clazz = Class.forName(CoreConst.DQC_CLASS);

            logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+")"+CoreConst.EXTERNAL_CLASSPATH);

            Method method = clazz.getDeclaredMethod("run", Map.class);
            Map<String,String> paras = new HashMap<String, String>();

            long timeMills = DateUtils.transferDateStr(inst.getCalDt());
            paras.put(Const.DQC_PARAM.task_id.toString(),String.valueOf(inst.getTaskId()));
            paras.put(Const.DQC_PARAM.schedule_time.toString(),String.valueOf(timeMills));
            paras.put(Const.DQC_PARAM.task_status_id.toString(),inst.getInstanceId());

            logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+") call dqc starts");
            TaskReturnDO rtnObj = (TaskReturnDO)method.invoke(clazz.newInstance(),paras);
            logger.warn(inst.getInstanceId() + "(" + inst.getTaskName() + ") call dqc ends");
            this.writeLogFile(inst, rtnObj.getMessage());
            return rtnObj;
        }catch(Throwable e){
            logger.warn(inst.getInstanceId() + "(" + inst.getTaskName() + ") call dqc error");
            logger.error(inst.getInstanceId()+"("+inst.getTaskName()+") external call error",e);
            return null;
            //throw new RuntimeException(inst.getInstanceId()+"("+inst.getTaskName()+") external call error",e);
        }
    }

    private void writeLogFile(InstanceDO inst,String msg){
        logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+") start write log");

        String logFile = inst.getLogPath();
        String snapshotLog = inst.getLogPath() + ".snapshot";
        try{
            FileUtils.writeStringToFile(new File(logFile),msg+"\n\r","utf-8",true);
            FileUtils.writeStringToFile(new File(snapshotLog),msg+"\n\r","utf-8",true);
        }catch(IOException e){
            logger.error("write external msg error",e);
        }
        logger.warn(inst.getInstanceId()+"("+inst.getTaskName()+") end write log");
    }

}
