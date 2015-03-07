package com.dianping.data.warehouse.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dianping.data.warehouse.core.common.TaskTestUtils;
import com.dianping.data.warehouse.halley.domain.TaskRelaDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
@Transactional
public class TaskRelaDAOTest {
	
	@Resource
	private TaskRelaDAO taskRelaDAO;
	
	@Test
	public void testGetTaskRelaByID(){
		int taskId = 10001;
		List<TaskRelaDO> taskRelaList = taskRelaDAO.getTaskRelaByID(taskId);
		assertEquals(TaskTestUtils.createExpectedTaskRelaList(), taskRelaList);
	}
	
	@Test
	public void testInsertTaskRela(){
		int taskId = 10001;
		List<TaskRelaDO> taskRelaList = taskRelaDAO.getTaskRelaByID(taskId);
		taskRelaDAO.deleteTaskRela(taskId);
		taskRelaDAO.insertTaskRela(taskRelaList);
		List<TaskRelaDO> result = taskRelaDAO.getTaskRelaByID(taskId);
		assertEquals(taskRelaList, result);
	}
	
	@Test
	public void testDeleteTaskRela(){
		int taskId = 10002;
		//List<TaskRelaDO> taskRelaList = taskRelaDAO.getTaskRelaByID(taskId);
		taskRelaDAO.deleteTaskRela(taskId);
		List<TaskRelaDO> result = taskRelaDAO.getTaskRelaByID(taskId);
		assertTrue(result.isEmpty());
		//restore deleted taskRelas
		//taskRelaDAO.insertTaskRela(taskRelaList);
	}

}
