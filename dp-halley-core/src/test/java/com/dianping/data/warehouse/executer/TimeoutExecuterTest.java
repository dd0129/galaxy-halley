package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.common.MockData;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.resource.RunningQueueManager;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-3-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
public class TimeoutExecuterTest {

    @Resource(name="timeoutExecuter")
    private TimeoutExecuter service;

    @BeforeClass
    public static void setup(){
        InstanceDO inst = MockData.genInstance();
        inst.setInstanceId("1000120120522");
        inst.setInQueueTimeMillis(System.currentTimeMillis()- 3600*1000*5);
        RunningQueueManager.inQueue(inst);

        InstanceDO inst1 = MockData.genInstance();
        inst1.setInstanceId("1000120120601");
        inst1.setInQueueTimeMillis(System.currentTimeMillis());
        RunningQueueManager.inQueue(inst1);
    }

    @Test
    public void testExecute() throws Exception {
        service.execute();
        Assert.assertEquals(String.valueOf(2),String.valueOf(RunningQueueManager.size()));
    }
}
