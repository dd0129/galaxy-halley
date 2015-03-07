package com.dianping.data.warehouse.worker.zk.node;

import com.dianping.data.warehouse.worker.zk.ZKNode;
import com.dianping.data.warehouse.worker.zk.consumer.PublishWorkerConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**                                                                         `
 * ServerNode
 */
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class WorkerNode extends ZKNode {
    private static final Logger logger = LoggerFactory.getLogger(WorkerNode.class);

    @Autowired
    private PublishWorkerConsumer WorkerConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init();
        this.jobQueueConsumer = WorkerConsumer;
        this.start();
        logger.info("worker node starts");
    }

}
