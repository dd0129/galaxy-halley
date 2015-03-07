package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.common.MockData;
import com.dianping.data.warehouse.domain.InstanceDO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by adima on 14-3-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
public class ReadyExecuterTest {
    @Resource(name="ready")
    private ReadyExecuter service;
    @BeforeClass
    public static void setup(){

    }
    @Test
    public void testUpdateDependency() throws Exception {
        InstanceDO inst = MockData.genInstance();
        inst.setIfPre(0);
        Method method = ReadyExecuter.class.getDeclaredMethod("updateTask", InstanceDO.class);
        method.setAccessible(true);
        boolean flag = (Boolean)method.invoke(service,inst);
        Assert.assertEquals(flag,true);
        inst.setIfPre(1);
        boolean flag1 = (Boolean)method.invoke(service,inst);
        Assert.assertEquals(flag1,true);
    }

    @Test
    public void testUpdate() throws Exception {
        service.execute();
        Assert.assertTrue(true);
    }
}
