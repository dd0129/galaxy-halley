package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.HalleyConst;
import com.dianping.data.warehouse.halley.service.TaskProducer;
import com.dianping.swallow.common.message.Destination;
import com.dianping.swallow.producer.Producer;
import com.dianping.swallow.producer.ProducerConfig;
import com.dianping.swallow.producer.ProducerMode;
import com.dianping.swallow.producer.impl.ProducerFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @rundemo_name 生产者例子(同步，可输入)
 */
public class TaskProducerImpl implements TaskProducer{
    private static Logger logger = LoggerFactory.getLogger(TaskProducerImpl.class);

    @Override
    public boolean publish(String msg){
        try{
            ProducerConfig config = new ProducerConfig();
            config.setMode(ProducerMode.ASYNC_MODE);
            Producer p = ProducerFactoryImpl.getInstance().createProducer(Destination.topic(HalleyConst.HALLEY_CASCADE_TOPIC), config);
            p.sendMessage(msg);
            return true;
        }catch(Exception e){
            logger.error("pulish topic "+ HalleyConst.HALLEY_CASCADE_TOPIC,e);
            return false;
        }
    }
}
