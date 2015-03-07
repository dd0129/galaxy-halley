package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongdi.tang on 14-3-28.
 */
public class RecallExecuter {
    @Resource(name="instanceDAO")
    private InstanceDAO instDAO;

    private static Logger logger = LoggerFactory.getLogger(RecallExecuter.class);
    public void execute(){
        try{
            logger.info("the recall executer starts");
            List<InstanceDO> list = this.instDAO.getFailInstanceList(Const.JOB_STATUS.JOB_FAIL.getValue());
            long now = System.currentTimeMillis();
            for(InstanceDO inst : list){
                try{
                    if(this.isExternalError(inst)){
                        continue;
                    } else if (inst.getIfRecall().intValue() == 1 && inst.getRecallNum() < inst.getRecallLimit()){
                        long endTime = Utilities.NDateUtils.getMills(inst.getEndTime());
                        if(now - endTime > inst.getRecallInterval() * 1000 * 60){
                            this.instDAO.updateInstRecall(
                                    inst.getInstanceId(),
                                    Const.JOB_STATUS.JOB_INIT.getValue(),
                                    Const.JOB_STATUS.JOB_INIT.getDesc());
                            logger.info(inst.getInstanceId()+"(" + inst.getTaskName() + ") recall success");
                        }
                    }
                }catch(Exception e){
                    logger.error(inst.getInstanceId()+"(" + inst.getTaskName() + ") recall fail",e);
                    this.instDAO.updateInstnaceStatus(
                            inst.getInstanceId(),
                            Const.JOB_STATUS.JOB_INTERNAL_ERROR.getValue(),
                            Const.JOB_STATUS.JOB_INTERNAL_ERROR.getDesc());
                }
            }
        }finally{
            logger.info("the recall executer ends");
        }
    }

    public boolean isExternalError(InstanceDO inst){
        Const.EXTERNAL_ERROR_CODES[] codes = Const.EXTERNAL_ERROR_CODES.values();
        for(Const.EXTERNAL_ERROR_CODES code : codes){
            if(inst.getJobCode().intValue() == code.getCode().intValue()){
                return true;
            }
        }
        return false;
    }
}
