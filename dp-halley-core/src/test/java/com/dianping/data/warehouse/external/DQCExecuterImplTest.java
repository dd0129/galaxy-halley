package com.dianping.data.warehouse.external;

import com.dianping.data.warehouse.common.MockData;
import com.dianping.data.warehouse.domain.InstanceDO;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-5-5.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-applicationContext_dqc.xml")
public class DQCExecuterImplTest {

//    @Resource(name="taskServiceDqcImpl")
//    private TaskServiceDqcImpl service;

    @Test
    public void testExecute() throws Exception {
//        service.run(null);
//        DQCExecuterImpl service = new DQCExecuterImpl();
//        InstanceDO inst = MockData.genInstance();
//        inst.setTaskId(10685);
//        service.execute(inst);
        //service.execute1(inst);
        ExternalExecuter service = new DQCExecuterImpl();
        InstanceDO inst = MockData.genInstance();
        inst.setTaskId(10685);
        service.execute(inst);
    }

//    public static void main(String[] args){
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-applicationContext_dqc.xml");
//        TaskServiceDqcImpl serivce = (TaskServiceDqcImpl)context.getBean("taskServiceDqcImpl");
//        InstanceDO inst = MockData.genInstance();
//        inst.setTaskId(10685);
//        Map<String,String> paras = new HashMap<String,String>();
//        paras.put(Const.DQC_PARAM.task_id.toString(),String.valueOf(inst.getTaskId()));
//        paras.put(Const.DQC_PARAM.schedule_time.toString(),String.valueOf(inst.getTriggerTime()));
//        paras.put(Const.DQC_PARAM.task_status_id.toString(),inst.getInstanceId());
//        serivce.run(paras);
//    }
}
