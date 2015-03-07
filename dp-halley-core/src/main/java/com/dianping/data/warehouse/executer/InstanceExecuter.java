package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.resource.ResourceManager2;
import com.dianping.data.warehouse.utils.DateFormatUtils;
import com.dianping.data.warehouse.utils.ExceptionAnalyze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by adima on 14-3-24.
 */
public class InstanceExecuter {
    private static Logger logger = LoggerFactory.getLogger(InstanceExecuter.class);
    @Resource
    private InstanceDAO instDAO;

    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private ExceptionAnalyze exceptionAnalyze;

    public InstanceExecuter(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void execute() throws Exception{
        try{
            logger.info("the execute thread starts");
            List<InstanceDO> list = this.instDAO.getReadyInstanceList(Const.JOB_STATUS.JOB_READY.getValue());
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            for (InstanceDO inst : list) {
                if (inst == null) {
                    continue;
                }
                try{
                    int size = ResourceManager2.inQueue(inst);
                    if(size==-1){
                        logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") resource:= "+inst.getDatabaseSrc() +
                                " score:= "+inst.getRunningPrio()+
                                " not join to queue");
                        continue;
                    }
                    logger.info(inst.getDatabaseSrc() +" " +size);

                    inst.setInQueueTimeMillis(System.currentTimeMillis());
                    logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") resource:= "+inst.getDatabaseSrc() +
                            " score:= "+inst.getRunningPrio()+
                            " join to Running Queue");

                    String currTime = DateFormatUtils.getFormatter().format(new Date());
                    this.instDAO.updateInstnaceRunning(inst.getInstanceId(), Const.JOB_STATUS.JOB_RUNNING.getValue(),
                            Const.JOB_STATUS.JOB_RUNNING.getDesc(), currTime);

                    Task2 task = new Task2(instDAO,inst,exceptionAnalyze);
                    Thread.sleep(2);
                    taskExecutor.submit(task);
                }catch(Exception e){
                    logger.error("instance execute error",e);
                }
            }
        }finally{
            logger.info("the execute thread ends");
        }


    }
}
