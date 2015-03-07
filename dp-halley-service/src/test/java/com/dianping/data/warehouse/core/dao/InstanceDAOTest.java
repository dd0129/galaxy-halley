package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.core.common.TaskTestUtils;
import com.dianping.data.warehouse.core.dao.InstanceDAO;
import com.dianping.data.warehouse.halley.domain.InstanceDO;
import com.dianping.data.warehouse.halley.domain.InstanceDisplayDO;
import com.dianping.data.warehouse.halley.domain.InstanceQueryDO;
import com.dianping.data.warehouse.halley.domain.TaskOwnerDO;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sunny on 14-5-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
@Transactional
public class InstanceDAOTest {
    @Resource
    private InstanceDAO instDAO;

    @Test
    public void testGetChildTaskStatusIdListByTaskStatusIdList() {
        List<String> inputTaskStatusIdList = new ArrayList<String>();
        inputTaskStatusIdList.add("2000712014052100");
        List<String> output = instDAO.getChildTaskStatusIdsByTaskStatusIds(inputTaskStatusIdList);
        List<String> expect = TaskTestUtils.createExpectSonTaskStatusIdListByTaskStatusIdList();
        assertEquals(output, expect);
        inputTaskStatusIdList.clear();
        inputTaskStatusIdList.add("2000712014052100__");
        output = instDAO.getChildTaskStatusIdsByTaskStatusIds(inputTaskStatusIdList);
        expect.clear();
        assertEquals(output, expect);
    }

    @Test
    public void testGetTaskStatusIdListByInstanceQueryDO() {
        InstanceQueryDO input = TaskTestUtils.createInputInstanceQueryDOByDate(0);
        List<String> output = instDAO.getTaskStatusIdsByInstanceQueryDO(input);
        List<String> expect = new ArrayList<String>();
        expect.add("8012752014052100");
        assertEquals(output, expect);
        input = TaskTestUtils.createInputInstanceQueryDOByDate(3);
        output = instDAO.getTaskStatusIdsByInstanceQueryDO(input);
        expect.clear();
        assertEquals(output, expect);
    }


    @Test
    public void testGetInstancesByTaskStatusIdList() {
        List<String> inputTaskStatusIds = new ArrayList<String>();
        inputTaskStatusIds.add("8012752014052100");
        List<InstanceDisplayDO> output = instDAO.getInstancesByTaskStatusIds(inputTaskStatusIds);
        List<InstanceDisplayDO> expect = TaskTestUtils.createExpectQueryTasksByDate(0);
        assertEquals(output, expect);
        inputTaskStatusIds.clear();
        inputTaskStatusIds.add("8012752014052100__");
        output = instDAO.getInstancesByTaskStatusIds(inputTaskStatusIds);
        expect.clear();
        assertEquals(output, expect);
    }

    @Test
    public void testGetDirectRelationTaskStatusIdListByTaskStatusId() {
        String inputTaskStatusId = "8012752014052100";
        List<String> output = instDAO.getDirectRelationTaskStatusIdsByTaskStatusId(inputTaskStatusId);
        List<String> expect = new ArrayList<String>();
        expect.add("2000712014052100");
        expect.add("6009512014052100");
        assertEquals(output, expect);
        inputTaskStatusId = "8012752014052100__";
        output = instDAO.getDirectRelationTaskStatusIdsByTaskStatusId(inputTaskStatusId);
        expect.clear();
        assertEquals(output, expect);
    }


    @Test
    public void testGetInstanceRelationsByTaskStatusIds() {
        List<String> input = new ArrayList<String>();
        input.add("8012752014052100");
        input.add("2000712014052100");
        List<Map<String, Object>> output = instDAO.getInstanceRelationsByTaskStatusIds(input);
        List<Map<String, Object>> expect = TaskTestUtils.createExpectMapList();
        assertEquals(output, expect);
        input.clear();
        input.add("8012752014052100");
        output = instDAO.getInstanceRelationsByTaskStatusIds(input);
        expect.clear();
        assertEquals(output, expect);
    }

    @Test
    public void testBatchStop() {
        String inputTaskStatusId = "801275";
        int output = instDAO.batchStop(inputTaskStatusId);
        int expect = 36;
        assertEquals(output, expect);
        inputTaskStatusId = "801275__";
        output = instDAO.batchStop(inputTaskStatusId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testRaisePriority() {
        String inputTaskId = "8012752014052100";
        int output = instDAO.raisePriority(inputTaskId);
        int expect = 1;
        assertEquals(output, expect);
        inputTaskId = "8012752014052100__";
        output = instDAO.batchStop(inputTaskId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testRecall() {
        String inputTaskId = "8012752014052100";
        int output = instDAO.recall(inputTaskId);
        int expect = 1;
        assertEquals(output, expect);
        inputTaskId = "8012752014052100__";
        output = instDAO.recall(inputTaskId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testSuspend() {
        String inputTaskId = "8012752014052100";
        int output = instDAO.suspend(inputTaskId);
        int expect = 1;
        assertEquals(output, expect);
        inputTaskId = "8012752014052100__";
        output = instDAO.suspend(inputTaskId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testSuccess() {
        String inputTaskId = "8012752014052100";
        int output = instDAO.success(inputTaskId);
        int expect = 1;
        assertEquals(output, expect);
        inputTaskId = "8012752014052100__";
        output = instDAO.success(inputTaskId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testGetInstanceDirectRelationsByInstanceId(){
        List<Map<String,Object>> list = instDAO.getInstanceDirectRelationsByInstanceId("4000212014090200");
        System.out.println(list.size());
    }

    @Test
    public void testGetInstancesForOncall(){
//        InstanceDO inst = instDAO.getInstancesForOncall(10567,"2014-09-02");
//        System.out.println(inst);
        DateTime dtime = DateTime.now();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
        System.out.println(dtime);
    }

    @Test
    public void testGetExceptionOncall(){
        System.out.println(instDAO.getExceptionResponsible(12));
    }

    @Test
    public void testGetOwnerByName(){
        TaskOwnerDO owner = instDAO.getOwnerByConditions(null,"product");
        System.out.println(owner);
    }

}
