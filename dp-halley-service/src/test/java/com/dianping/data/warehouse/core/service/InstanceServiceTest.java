package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.TaskTestUtils;
import com.dianping.data.warehouse.halley.domain.InstanceDisplayDO;
import com.dianping.data.warehouse.halley.domain.InstanceQueryDO;
import com.dianping.data.warehouse.halley.service.InstanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sunny on 14-7-7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-applicationcontext.xml", "classpath:spring-beans.xml"})
public class InstanceServiceTest {
    @Resource(name = "instanceService")
    private InstanceService instanceService;

    @Test
    public void testQueryInstancesByDate() {
        InstanceQueryDO instanceQueryDO;
        List<InstanceDisplayDO> outputInstanceDisplayDOList, expectInstanceDisplayDOList;
        for (int i = 0; i < 12; i++) {
            instanceQueryDO = TaskTestUtils.createInputInstanceQueryDOByDate(i);
            outputInstanceDisplayDOList = instanceService.queryInstancesByDate(instanceQueryDO);
            expectInstanceDisplayDOList = TaskTestUtils.createExpectQueryTasksByDate(i);
            assertEquals(outputInstanceDisplayDOList, expectInstanceDisplayDOList);
        }
    }

    @Test
    public void testQueryInstancesByInterval() {
        InstanceQueryDO instanceQueryDO;
        List<InstanceDisplayDO> outputInstanceDisplayDOList, expectInstanceDisplayDOList;
        for (int i = 0; i < 12; i++) {
            instanceQueryDO = TaskTestUtils.createInputInstanceQueryDOByInterval(i);
            outputInstanceDisplayDOList = instanceService.queryInstancesByDate(instanceQueryDO);
            expectInstanceDisplayDOList = TaskTestUtils.createExpectQueryTasksByInterval(i);
            assertEquals(outputInstanceDisplayDOList, expectInstanceDisplayDOList);
        }
    }

    @Test
    public void testGetTaskStatus() {
        String inputTaskStatusId = "8012752014052100";
        InstanceDisplayDO outputInstanceDisplayDO = instanceService.getInstanceByInstanceId(inputTaskStatusId);
        InstanceDisplayDO expectInstanceDisplayDO = TaskTestUtils.createExpectGetTaskStatus();
        assertEquals(outputInstanceDisplayDO, expectInstanceDisplayDO);

        inputTaskStatusId = "8012752014052100__";
        outputInstanceDisplayDO = instanceService.getInstanceByInstanceId(inputTaskStatusId);
        expectInstanceDisplayDO = null;
        assertEquals(outputInstanceDisplayDO, expectInstanceDisplayDO);
    }

    @Test
    public void testQueryDirectRelation() {
        String inputTaskStatusId = "8012752014052100";
        List<InstanceDisplayDO> outputInstanceDisplayDOList = instanceService.queryDirectRelation(inputTaskStatusId);
        List<InstanceDisplayDO> expectInstanceDisplayDOList = TaskTestUtils.createExpectQueryDirectRelation();
        assertEquals(outputInstanceDisplayDOList, expectInstanceDisplayDOList);

        inputTaskStatusId = "8012752014052100__";
        outputInstanceDisplayDOList = instanceService.queryDirectRelation(inputTaskStatusId);
        expectInstanceDisplayDOList = new ArrayList<InstanceDisplayDO>();
        assertEquals(outputInstanceDisplayDOList, expectInstanceDisplayDOList);
    }

    @Test
    public void testAllDirectRelation() {
        String inputTaskStatusId = "8012752014052100";
        List<InstanceDisplayDO> outputInstanceDisplayDOList = instanceService.queryAllRelation(inputTaskStatusId);
        List<InstanceDisplayDO> expectInstanceDisplayDOList = TaskTestUtils.createExpectQueryAllRelation();
        assertEquals(outputInstanceDisplayDOList, expectInstanceDisplayDOList);

        inputTaskStatusId = "8012752014052100__";
        outputInstanceDisplayDOList = instanceService.queryDirectRelation(inputTaskStatusId);
        expectInstanceDisplayDOList = new ArrayList<InstanceDisplayDO>();
        assertEquals(outputInstanceDisplayDOList, expectInstanceDisplayDOList);
    }

    @Test
    public void testRecall() {
        String inputTaskStatusId = "8012752014052100";
        int output = instanceService.recallInstance(inputTaskStatusId);
        int expect = 1;
        assertEquals(output, expect);

        inputTaskStatusId = "8012752014052100";
        output = instanceService.recallInstance(inputTaskStatusId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testSuspend() {
        String inputTaskStatusId = "8012752014052100";
        int output = instanceService.suspendInstance(inputTaskStatusId);
        int expect = 1;
        assertEquals(output, expect);

        inputTaskStatusId = "8012752014052100";
        output = instanceService.suspendInstance(inputTaskStatusId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testSuccess() {
        String inputTaskStatusId = "8012752014052100";
        int output = instanceService.successInstance(inputTaskStatusId);
        int expect = 1;
        assertEquals(output, expect);

        inputTaskStatusId = "8012752014052100";
        output = instanceService.successInstance(inputTaskStatusId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testRaisePriority() {
        String inputTaskStatusId = "8012752014052100";
        int output = instanceService.raisePriorityInstance(inputTaskStatusId);
        int expect = 1;
        assertEquals(output, expect);

        inputTaskStatusId = "8012752014052100";
        output = instanceService.raisePriorityInstance(inputTaskStatusId);
        expect = 0;
        assertEquals(output, expect);
    }

    @Test
    public void testBatchStop() {
        String inputTaskId = "801275";
        int output = instanceService.batchStopTask(inputTaskId);
        int expect = 36;
        assertEquals(output, expect);

        inputTaskId = "8012752014052100__";
        output = instanceService.batchStopTask(inputTaskId);
        expect = 0;
        assertEquals(output, expect);
    }
}
