package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.halley.service.TaskProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hongdi.tang on 14-4-3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-applicationcontext.xml")
public class TaskProducerImplTest {
    @Test
    public void testPublish() throws Exception {
        TaskProducer producer = new TaskProducerImpl();
        producer.publish("fuck");
    }

}
