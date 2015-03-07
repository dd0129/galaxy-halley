package com.dianping.data.warehouse.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-6-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class PublishServiceTest {

    @Resource(name="taskPublishImpl")
    private TaskPublishImpl service;
    @Test
    public void testCallback() throws Exception {

    }

    @Test
    public void testPublish() throws Exception {
        service.publish("data_analysis","/es_csc/dol/dpmid_rule_pem_case_add.dol");
    }
}
