package com.dianping.data.warehouse.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dianping.data.warehouse.halley.domain.InstanceRelaDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dianping.data.warehouse.core.common.TaskTestUtils;
import com.dianping.data.warehouse.halley.domain.TaskDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
@Transactional
public class TaskDAOTest {

    @Resource
    private TaskDAO taskDAO;

    @Test
    public void testGetTaskByID() {
        Integer id = 10001;
        TaskDO task = taskDAO.getTaskByTaskId(id);
        assertEquals(TaskTestUtils.createExpectTaskDO(null), task);
    }

    @Test
    public void testGetTasksByIDs() {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(10001);
        ids.add(10002);
        List<TaskDO> tasks = taskDAO.getValidTasksByTaskIds(ids);
        assertEquals(TaskTestUtils.createExpectTaskDO(null), tasks);
    }

    @Test
    public void testGetAllTasks() {
//        List<TaskDO> tasks = taskDAO.getAllTasks();

//        boolean result = tasks.contains(TaskTestUtils.createExpectTaskDOForGetAll(null));
//        assertEquals(result, true);
    }

    @Test
    public void testGet() {
        List<Integer> taskIdList = taskDAO.getTaskIDList();
        List<Integer> result = new ArrayList<Integer>();
        result.add(10001);
        result.add(10002);
        result.add(10003);
        assertEquals(result, taskIdList);
    }

    @Test
    public void testDeleteTask() {
        Integer id = 10001;
        //TaskDO task = taskDAO.getTaskByID(id);
        taskDAO.deleteTask(id, "2014-11-11", "ning.sun");
        TaskDO result = taskDAO.getTaskByTaskId(id);
        assertNull(result);
        //restore deleted task
        //taskDAO.insertTask(task);
    }

    @Test
    public void testInsertTask() {
        Integer id = 10002;
        TaskDO task = taskDAO.getTaskByTaskId(id);
        taskDAO.deleteTask(id, "2014-11-11", "ning.sun");
        taskDAO.insertTask(task);
        TaskDO result = taskDAO.getTaskByTaskId(id);
        assertEquals(task, result);
    }

    @Test
    public void testUpdateTask() {
        Integer id = 10002;
        TaskDO origin = taskDAO.getTaskByTaskId(id);
        TaskDO task = taskDAO.getTaskByTaskId(id);
        task.setIfWait(1000);
        task.setUpdateUser("yix.zhang");
        task.setIfVal(100);
        task.setFreq("11 5 0 * * ?");
        task.setIfRecall(2);
        taskDAO.updateTask(task);
        TaskDO result = taskDAO.getTaskByTaskId(id);
        assertEquals(task, result);
        //restore updated task
        taskDAO.deleteTask(id, "2014-11-11", "ning.sun");
        taskDAO.insertTask(origin);
    }

    @Test
    public void testUpdateTaskStatus() {
        Integer id = 10002;
        Integer ifVal = 5;
        taskDAO.updateTaskStatus(id, ifVal, "2014-01-18 22:06:03", "ning.sun");
        TaskDO result = taskDAO.getTaskByTaskId(id);
        assertEquals(result.getIfVal(), ifVal);
    }

    @Test
    public void testGetTaskIdByTaskName() {
        String taskName = "hive##test.hongdi";
        assertEquals(10832, (int) taskDAO.getTaskIdByTaskName(taskName));
        taskName = "hive##test.hongdi_test";
        assertEquals(null, taskDAO.getTaskIdByTaskName(taskName));
    }

    @Test
    public void testGetTableIdByTableName() {
        String tableName = "dpods_acl_user_info";
//        assertEquals(11067945, (int) taskDAO.getTableIdByTableName(tableName));
        tableName = "dpods_acl_user_info_test";
        assertEquals(null, taskDAO.getTaskIdByTaskName(tableName));
    }

    @Test
    public void testInsertInstanceRela() {
        InstanceRelaDO relaDO = new InstanceRelaDO();
        relaDO.setPreInstanceId("12222");
        relaDO.setTaskId(122);
        relaDO.setPreInstanceId("11111");
        relaDO.setPreId(111);
        //relaDO.setTimestamp();
    }

    @Test
    public void testGetParentTaskIdsByTables() {
        List<String> tables = new ArrayList<String>();
        tables.add("dpods_acl_table_privs");
        tables.add("dpdw_traffic_base");
        tables.add("dpods_acl_table_privs");
        tables.add("dpdm_user_shop_base");
        List<Map<String, Integer>> map = taskDAO.getParentTaskIdsByTables(tables);
        System.out.println(map);
    }
}
