package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.TaskTestUtils;
import com.dianping.data.warehouse.core.dao.TaskRelaDAO;
import com.dianping.data.warehouse.halley.domain.TaskDO;
import com.dianping.data.warehouse.halley.domain.TaskQueryDO;
import com.dianping.data.warehouse.halley.domain.TaskRelaDO;
import com.dianping.data.warehouse.halley.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-applicationcontext.xml", "classpath:spring-beans.xml"})
public class TaskServiceImplTest {

    @Resource(name = "taskService")
    private TaskService taskService;

    //    @Test
    public void testGetTaskID() {
        int id = 10001;
        TaskDO result = taskService.getTaskByTaskId(id);
        TaskDO expected = TaskTestUtils.createExpectTaskDO(TaskTestUtils.createExpectedTaskRelaList());
        assertEquals(expected, result);
    }

    @Test
    public void testUpdateTask() {

        List<TaskRelaDO> relaDOList = TaskTestUtils.createExpectedTaskRelaList();
        TaskDO expect = TaskTestUtils.createExpectTaskDO(null);
        expect.setUpdateUser("shanshan");
        try {
            taskService.updateTask(expect);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        TaskDO result = taskService.getTaskByTaskId(10001);
        assertEquals(expect, result);
        //restore orignal value
        List<TaskRelaDO> oriRelaDOList = TaskTestUtils.createExpectedTaskRelaList();
        TaskDO oriTask = TaskTestUtils.createExpectTaskDO(oriRelaDOList);
        taskService.updateTask(oriTask);
    }

    @Test
    public void testGetAllChildrenOfQueryInstanceId() {
        //List<InstanceChildrenDO> list = taskService.getAllChildrenOfQueryInstanceId("102262014062500","2014-06-25");
//        List<InstanceChildrenDO> list = taskService.getAllChildrenOfQueryInstanceId("102252014062500", "2014-06-25");
//        System.out.println(list);
    }


    private TaskRelaDAO replaceTaskRelaDAOByMock() {
        TaskRelaDAO mockTaskRelaDAO = Mockito.mock(TaskRelaDAO.class);
        List<TaskRelaDO> relaDOList = TaskTestUtils.createExpectedTaskRelaList();
        doThrow(new RuntimeException()).when(mockTaskRelaDAO).deleteTaskRela(10001);
        doThrow(new RuntimeException()).when(mockTaskRelaDAO).insertTaskRela(relaDOList);
        when(mockTaskRelaDAO.getTaskRelaByID(10001)).thenReturn(relaDOList);
        if (!(taskService instanceof TaskServiceImpl)) fail();
        TaskServiceImpl impl = ((TaskServiceImpl) taskService);
        TaskRelaDAO origin = impl.getTaskRelaDAO();
        impl.setTaskRelaDAO(mockTaskRelaDAO);
        return origin;
    }

    private void restoreTaskRelaDAO(TaskRelaDAO origin) {
        if (!(taskService instanceof TaskServiceImpl)) fail();
        TaskServiceImpl impl = ((TaskServiceImpl) taskService);
        impl.setTaskRelaDAO(origin);
    }

    @Test
    public void testDeleteTaskByIDTransactional() {
        TaskRelaDAO origin = replaceTaskRelaDAOByMock();
        try {
            taskService.deleteTaskByTaskId(10001, "2014-11-11", "ning.sun");
            fail("taskService.deleteTaskByID does not throw exception as expected");
        } catch (RuntimeException ex) {
        }
        restoreTaskRelaDAO(origin);

        TaskDO taskDO = taskService.getTaskByTaskId(10001);
        assertNotNull(taskDO);
    }

    @Test
    public void testInsertTaskTransactional() {
        taskService.deleteTaskByTaskId(10001, "2014-11-11", "ning.sun");
        TaskDO result = taskService.getTaskByTaskId(10001);
        assertNull(result);
        TaskRelaDAO origin = replaceTaskRelaDAOByMock();
        try {
            taskService.insertTask(TaskTestUtils.createExpectTaskDO(TaskTestUtils.createExpectedTaskRelaList()));
            fail("taskService.insertTask does not throw exception as expected");
        } catch (RuntimeException ex) {
            System.out.println("exception catched");
            ex.printStackTrace();
        }
        restoreTaskRelaDAO(origin);
        result = taskService.getTaskByTaskId(10001);
        assertNull(result);
        List<TaskRelaDO> list = new ArrayList<TaskRelaDO>();
        TaskRelaDO relaDO = new TaskRelaDO();
        relaDO.setTaskId(10001);
        relaDO.setCycleGap("D0");
        relaDO.setTaskPreId(10002);
        relaDO.setTimeStamp("2014-08-27");
        list.add(relaDO);
        taskService.insertTask(TaskTestUtils.createExpectTaskDO(list));
    }

    @Test
    public void testInsertTask() {
        taskService.deleteTaskByTaskId(10001, "2014-11-11", "ning.sun");
        TaskDO result = taskService.getTaskByTaskId(10001);
        TaskTestUtils.createExpectTaskDO(null);

        taskService.insertTask(TaskTestUtils.createExpectTaskDO(null));
    }

    @Test
    public void testUpdateTaskTransactional() {
        TaskDO expected = taskService.getTaskByTaskId(10001);
        TaskRelaDAO origin = replaceTaskRelaDAOByMock();
        try {
            TaskDO insertTaskDO = TaskTestUtils.createExpectTaskDO(TaskTestUtils.createExpectedTaskRelaList());
            insertTaskDO.setTaskName("XXXXXXXXX");
            insertTaskDO.setTableName("XXXXXXXXX");
            taskService.insertTask(insertTaskDO);
            fail("taskService.insertTask does not throw exception as expected");
        } catch (RuntimeException ex) {
            System.out.println("exception catched");
        }
        restoreTaskRelaDAO(origin);
        TaskDO result = taskService.getTaskByTaskId(10001);
        assertEquals(expected, result);
    }

    @Test
    public void testGenerateTaskIDNormalCase() {
        TaskDO taskDO = TaskTestUtils.createExpectTaskDO(TaskTestUtils.createExpectedTaskRelaList());
        Integer result = taskService.generateTaskID(taskDO);
        Integer expected = 10004;
        assertEquals(expected, result);
    }

    @Test
    public void testExistTaskName() {
        String taskName = "hive##bi.dpdw_mobile_log_m";
        assertEquals(true, taskService.taskNameExists(taskName));
        taskName = "hive##bi.dpdw_mobile_log_m_test";
        assertEquals(false, taskService.taskNameExists(taskName));
    }


    public void testGetTasksByConditions() {
        int jobGroup = 1;
        String jobExecCycle = "D";
        String jobDeveloper = "yifan.cao";
        String jobName = "";
        TaskQueryDO taskQueryDO = new TaskQueryDO();
        taskQueryDO.setGroup(jobGroup);
        taskQueryDO.setCycle(jobExecCycle);
        taskQueryDO.setDeveloper(jobDeveloper);
        taskQueryDO.setTaskName(jobName);
        List<TaskDO> taskDOList = taskService.queryTasks(taskQueryDO);
        System.out.print(taskDOList.size());
        assertEquals(3, taskDOList.size());
    }

    @Test
    public void testPreRunJob() {
        String startTime = "2014-5-13 17:00:00";
        String endTime = "2014-5-14 17:00:00";
//        List<Integer> tasks = new ArrayList<Integer>();
//        tasks.add(new Integer(10001));
        boolean result = taskService.preRunTask(10001, startTime, endTime, "tao.meng");
    }

    @Test
    public void testGetTaskLogContent() {
//        String str = taskService.getTaskLogContent("/data/deploy/dwarch/log/ETL/wormhole/dpods_dp_shopdishtag.102592014061600.20140615");
//        System.out.println(str);

        String s = "";
        System.out.println(s.equals(null));

    }

    @Test
    public void testPrerunAsending() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10003);
        list.add(10002);
        taskService.preRunJobAscend(10003, "2014-08-05", "2014-09-01", "hongdi.tang");
        taskService.preRunJobAscend(10002, "2014-08-05", "2014-09-01", "hongdi.tang");
    }
}
