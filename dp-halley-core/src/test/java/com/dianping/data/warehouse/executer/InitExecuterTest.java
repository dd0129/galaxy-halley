package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.dao.proxy.InstanceDAOProxy;
import com.dianping.data.warehouse.dao.TaskDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.domain.TaskDO;
import com.dianping.data.warehouse.domain.TaskRelaDO;
import com.dianping.data.warehouse.validator.TaskValidator;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronExpression;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-3-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
public class InitExecuterTest {
    @Resource(name = "taskDAO")
    private TaskDAO taskDAO;

    @Resource(name="init")
    private InitExecuter instanceInit;

    @Resource(name="instDAOProxy")
    private InstanceDAOProxy instDAOProxy;

    private List<TaskDO> list;
    private List<TaskRelaDO> relaList;
    private Map<Integer,TaskDO> taskMap;
    private Map<Integer,List<TaskRelaDO>> relaMap;
    private Map<Integer,List<TaskRelaDO>> reverseMap;

    @Before
    public void setup() throws Exception{
//        list = taskDAO.getValidateTaskList(CoreConst.TASK_VALIDATE);
//        relaList =  taskDAO.getTaskRelaList();
//        Method method = InitExecuter.class.getDeclaredMethod("convertRelaMap", List.class);
//        method.setAccessible(true);
//        relaMap = (Map<Integer,List<TaskRelaDO>>)method.invoke(instanceInit,relaList);
//
//        Method method1 = InitExecuter.class.getDeclaredMethod("convertTaskMap", List.class);
//        method1.setAccessible(true);
//        taskMap = (Map<Integer,TaskDO>)method1.invoke(instanceInit,list);
//
//        Method method2 = InitExecuter.class.getDeclaredMethod("convertReverseRelaMap", List.class);
//        method2.setAccessible(true);
//        reverseMap = (Map<Integer,List<TaskRelaDO>> )method2.invoke(instanceInit,relaList);
    }

    @Test
    public void testConvertRelaMap() throws Exception{
        Method method = InitExecuter.class.getDeclaredMethod("convertRelaMap", List.class);
        method.setAccessible(true);
        Map<Integer,List<TaskRelaDO>> output = (Map<Integer,List<TaskRelaDO>>)
                method.invoke(instanceInit,relaList);
        Assert.assertNotNull(output.size());
        Assert.assertSame(output.get(500832).size(),46);
    }

    @Test
    public void testConvertTaskMap() throws Exception{
        Method method = InitExecuter.class.getDeclaredMethod("convertTaskMap", List.class);
        method.setAccessible(true);
        Map<Integer,TaskDO> output = (Map<Integer,TaskDO>)method.invoke(instanceInit,list);
        Assert.assertNotNull(output.size());
        //Assert.assertEquals(output.size(), 4827);
        Assert.assertEquals(output.get(200531).getCycle(), "D");
    }

    @Test
    public void testSaveInstance() throws Exception{
//        Method method = InitExecuter.class.getDeclaredMethod("generateInstance", TaskDO.class, List.class, Date.class);
//        method.setAccessible(true);
//        int i = 1;
//        for(TaskDO task : list){
//            try{
//                List<TaskRelaDO> relaList = relaMap.get(task.getTaskId());
//                InstanceDO output = (InstanceDO)method.invoke(instanceInit,task,relaList,new Date());
//                output.setRunningPrio(0);
//                instDAOProxy.saveInstance(output,++i);
//            }catch(Exception e){
//                e.printStackTrace();
//
//            }
//        }
        //this.instDAOProxy.saveList(list,relaMap,instanceInit);
    }

    @Test
    public void testGenerateInstance() throws Exception{
        Method method = InitExecuter.class.getDeclaredMethod("generateInstance", TaskDO.class,List.class,Date.class);
        TaskDO task = list.get(2);
        List<TaskRelaDO> relaList = relaMap.get(task.getTaskId());
        method.setAccessible(true);
        InstanceDO output = (InstanceDO)method.invoke(instanceInit,task,relaList,new Date());

        Assert.assertNotNull(output.getInstanceId());
        Assert.assertNotNull(output.getCycle());
        Assert.assertNotNull(output.getSuccessCode());
        Assert.assertNotNull(output.getIfRecall());
        Assert.assertNotNull(output.getIfWait());
        Assert.assertNotNull(output.getIfPre());

    }

    @Test
    public void testConvertReverseRelaMap() throws Exception{
        Method method = InitExecuter.class.getDeclaredMethod("convertReverseRelaMap", List.class);
        method.setAccessible(true);
        Map<Integer,List<TaskRelaDO>> reverseMap = (Map<Integer,List<TaskRelaDO>> )method.invoke(instanceInit,relaList);

        Assert.assertNotNull(reverseMap);
        //Assert.assertEquals(reverseMap.get(60002).size(), 375);
        int size = 0 ;
        for(List<TaskRelaDO> list : reverseMap.values()){
            size = size + list.size();
        }
        Assert.assertEquals(size, relaList.size());
        Assert.assertEquals(reverseMap.get(60002).size(), 373);
        Assert.assertEquals(reverseMap.get(200792).size(), 88);
        Assert.assertEquals(reverseMap.get(300283).size(), 56);
    }

    @Test
    public void testDynamicPriority() throws Exception{
        for(TaskDO task : list){
            InitExecuter.DynamicPriority dp = instanceInit.new DynamicPriority(task.getTaskId(),task.getPrioLvl(),taskMap,reverseMap);
            Integer score = dp.calculateScore(task.getTaskId(),task.getPrioLvl());
            System.out.println(task.getTaskId()+" "+task.getTaskName() + "score:=" +score);
            Assert.assertNotNull(score);
        }
    }

    @Test
    public void testExecute(){
        instanceInit.execute();
        Assert.assertTrue(true);
    }

    @Test
    public void testCronExperssion() throws Exception{
        CronExpression expression = new CronExpression("10 5 0 * * ?");
        Date d1 = new Date();
        Date d = d1;
        //while(true){
            d = expression.getNextValidTimeAfter(d);
            System.out.println(d);
        //}
        Assert.assertTrue(true);
    }

    @Test
    public void testPara() throws Exception{

    }

    @Test
    public void testValidateTask() throws Exception {
        List<TaskDO> list = taskDAO.getValidateTaskList(1);
        for(TaskDO task : list){
            String[] rtn =TaskValidator.validateCycle(task.getCycle());
            String[] rtn1 =TaskValidator.validateTask(task);
            if(rtn[0].equals("0") || rtn1[0].equals("0")){
                System.out.println(task.getTaskId()+ "(" +task.getTaskName()+")");
                System.out.println(rtn[1]);
                System.out.println(rtn1[1]);
            }
        }
    }




}
