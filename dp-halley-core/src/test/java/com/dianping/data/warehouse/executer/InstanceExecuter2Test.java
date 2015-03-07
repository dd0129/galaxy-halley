package com.dianping.data.warehouse.executer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by adima on 14-4-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
public class InstanceExecuter2Test {
    @Resource(name="execute")
    private InstanceExecuter service;
    @Test
    public void testExecute() throws Exception {
       // while(true){
            service.execute();
       // }
    }
}
