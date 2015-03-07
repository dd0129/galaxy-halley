package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.halley.client.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-3-28.
 */
public class ResetExecuter {
    @Resource(name="instanceDAO")
    private InstanceDAO instDAO;

    private static Logger logger = LoggerFactory.getLogger(ResetExecuter.class);
    public void execute(){
        try{
            logger.info("the reset executer starts");
            Integer num = this.instDAO.resetInstance(Const.JOB_STATUS.JOB_INIT.getValue(),
                    Const.JOB_STATUS.JOB_INIT.getDesc(),
                    Const.JOB_STATUS.JOB_RUNNING.getValue(),
                    Const.JOB_STATUS.JOB_TIMEOUT.getValue());
            logger.info("reset update task num "+num);
        }finally{
            logger.info("the reset executer ends");
        }
    }
}
