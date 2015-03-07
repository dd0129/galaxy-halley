package com.dianping.data.warehouse.ssh;

import com.dianping.data.warehouse.halley.client.TaskClient;
import com.dianping.data.warehouse.halley.domain.TaskReturnDO;
import com.dianping.data.warehouse.masterdata.service.MercuryService;
import com.dianping.dqc.pigeon.client.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.Random;


public class TaskServiceDqcImpl  implements TaskClient{
    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceDqcImpl.class);
    private static final int DEFAULT_ERROR_STATUS = 501;
    private static final int RETRY_MAX = 5;
    private static final int BASE_SLEEP_TIMEMS = 2000;
    private static final int MAX_SLEEP_MS = 1800000;
    private final Random random = new Random();

//    public TaskServiceDqcImpl(){
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test-spring-applicationContext.xml");
//    }

    public MercuryService getMercuryService() {
        return mercuryService;
    }

    public void setMercuryService(MercuryService mercuryService) {
        this.mercuryService = mercuryService;
    }

    private MercuryService mercuryService;
    private ScheduleJobService dqcService;

    public ScheduleJobService getDqcService() {
        return dqcService;
    }

    public void setDqcService(ScheduleJobService dqcService) {
        this.dqcService = dqcService;
    }

    public TaskReturnDO run(Map<String, String> params) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test-spring-applicationContext.xml");

        TaskServiceDqcImpl taskServiceDqc = (TaskServiceDqcImpl)context.getBean("taskServiceDqcImpl");
        System.out.println(taskServiceDqc);
        System.out.println("fuck");
        return null;
        //return taskServiceDqc.submitAndCheck(params);
    }

    public static void main(String[] args){
        TaskServiceDqcImpl service = new TaskServiceDqcImpl();
        service.run(null);
    }
}