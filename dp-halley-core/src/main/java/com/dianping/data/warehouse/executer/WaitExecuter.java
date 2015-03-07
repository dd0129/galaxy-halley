package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.halley.client.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by adima on 14-3-24.
 */
public class WaitExecuter {

    private static Logger logger = LoggerFactory.getLogger(WaitExecuter.class);
    @Resource(name="instanceDAO")
    private InstanceDAO instDAO;

    public void execute(){
        try{
            logger.info("the waitInit starts");
            Integer num = this.instDAO.updateInstnaceListStatus(Const.JOB_STATUS.JOB_INIT.getValue(),
                    Const.JOB_STATUS.JOB_INIT.getDesc(),
                    Const.JOB_STATUS.JOB_WAIT.getValue());
            logger.info("wait executer update task num "+num);
        }finally{
            logger.info("the waitInit ends");
        }
    }

}
