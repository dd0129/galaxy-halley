package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.resource.ResourceManager2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by adima on 14-3-24.
 */
public class TimeoutExecuter {
    private static Logger logger = LoggerFactory.getLogger(TimeoutExecuter.class);


    @Resource(name="instanceDAO")
    private InstanceDAO instDAO;

    public void execute(){
        try{
            logger.info("the timeout execute process starts");
            for (InstanceDO inst : ResourceManager2.values()) {
                try {
                    Long startTime = inst.getInQueueTimeMillis();
                    Long currentTime = System.currentTimeMillis();
                    if (currentTime - startTime > inst.getTimeout() * 60 * 1000) {
                        logger.info(inst.getInstanceId()+"(" +inst.getTaskName()+") is timeout" +
                                " inqueue time ="+ startTime + " timeout :="+inst.getTimeout() +" minutes");
                        this.instDAO.updateInstnaceStatus(inst.getInstanceId(), Const.JOB_STATUS.JOB_TIMEOUT.getValue(),
                                Const.JOB_STATUS.JOB_TIMEOUT.getDesc());
                        ResourceManager2.setInstStatus(inst,Const.JOB_STATUS.JOB_TIMEOUT.getValue());
                    }
                } catch (Exception e) {
                    logger.error(inst.getInstanceId()+"("+inst.getTaskName()+") set timeout error", e);
                }
            }
        }finally{
            logger.info("the timeout execute process ends");
        }
    }
}
