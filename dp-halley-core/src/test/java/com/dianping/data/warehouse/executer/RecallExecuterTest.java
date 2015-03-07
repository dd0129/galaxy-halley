package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.domain.InstanceDO;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-4-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
public class RecallExecuterTest {

    @Resource(name="recall")
    private RecallExecuter recall;

    @Test
    public void testExecute() throws Exception {
        recall.execute();
        Assert.assertTrue(true);
    }

    @Test
    public void testIsExternalError(){
        InstanceDO inst = new InstanceDO();
        inst.setJobCode(301);
        boolean flag = recall.isExternalError(inst);
        Assert.assertTrue(flag);
    }
}
