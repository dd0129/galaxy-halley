package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.common.GlobalResource;
import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.resource.ResourceManager2;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by hongdi.tang on 14-3-24.
 */
public class SynchronizeExecuter {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizeExecuter.class);

    @Resource
    private InstanceDAO instDAO;

    public void execute() {
        try {
            logger.info("the SynchronizeExecuter thread starts");
            long currentTime = System.currentTimeMillis();
            for (InstanceDO inst : ResourceManager2.values()) {
                try{
                    if(inst.getStatus() != Const.JOB_STATUS.JOB_RUNNING.getValue() ||
                            inst.getStatus() != Const.JOB_STATUS.JOB_TIMEOUT.getValue()){
                        ResourceManager2.outQueue(inst);
                        continue;
                    }
                    Integer status = instDAO.getInstanceInfo(inst.getInstanceId()).getStatus();
//                    if (status != Const.JOB_STATUS.JOB_RUNNING.getValue() &&
//                            status != Const.JOB_STATUS.JOB_TIMEOUT.getValue()) {
                    if (status != inst.getStatus()) {
                        logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") status is, " + inst.getStatus() +
                                " in queue time at" + String.valueOf(inst.getInQueueTimeMillis()) + " has been removed");
                        ResourceManager2.outQueue(inst);
                        ResourceManager2.destoryProcess(inst.getInstanceId());
                    }else{
                        logger.info(inst.getInstanceId() + "(" + inst.getTaskName() +
                                ") in queue time " + inst.getInQueueTimeMillis()+", detect log file"+inst.getLogPath().concat(".snapshot"));
                        File file = new File(inst.getLogPath().trim().concat(".snapshot"));
                        if(file.exists()){
                            boolean isOlder = FileUtils.isFileOlder(file, currentTime - CoreConst.MAX_HUNG_TIME);
                            long size = FileUtils.sizeOf(file);
                            if(size>0L && size < CoreConst.MIN_LOG_SIZE && isOlder && inst.getTaskGroupId()!=8 && inst.getTaskGroupId()!=5){
                                logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") execute hung");
                                ResourceManager2.outQueue(inst);
                                this.instDAO.updateInstRecall(
                                        inst.getInstanceId(),
                                        Const.JOB_STATUS.JOB_INIT.getValue(),
                                        Const.JOB_STATUS.JOB_INIT.getDesc());
                                //ResourceManager2.destoryProcess(inst.getInstanceId());
                            }
                        }
                    }
                }catch(Exception e){
                    logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") synchronize error",e);
                }
            }
        } finally {
            logger.info("the synchronize thread ends");
        }
    }

}
