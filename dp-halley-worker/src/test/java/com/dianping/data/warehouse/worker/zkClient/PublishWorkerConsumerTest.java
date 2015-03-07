package com.dianping.data.warehouse.worker.zkClient;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import com.dianping.data.warehouse.worker.zk.consumer.PublishWorkerConsumer;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-6-18.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class PublishWorkerConsumerTest {
    @Resource(name="publishWorkerConsumter")
    private PublishWorkerConsumer consumer;

    @Test
    public void testConsumeMessage() throws Exception {
        consumer.consumeMessage(new PublishFileDO("d:/test","test.dol"));
    }

    @Test
    public void testGetHostIP() throws Exception {
    }

    @Test
    public void testGetLocalFile() throws Exception{
        PublishWorkerConsumer consumer1 = new PublishWorkerConsumer();
        PublishFileDO file = new PublishFileDO();
        file.setProjectName("warehouse");
        file.setFilename("warehouse/dol/fuck.txt");
        System.out.println(consumer1.getLocalFile(file));
        file.setProjectName("data_analysis");
        file.setFilename("es_csc/dol/dpmid_rule_pem_case_add.dol");
        System.out.println(consumer1.getLocalFile(file));
    }
}
