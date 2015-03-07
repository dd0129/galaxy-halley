package com.dianping.data.warehouse.core.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * ServerNode
 */
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ServerNode extends ZKNode {

    private static final Logger logger = LoggerFactory.getLogger(ServerNode.class);

    @Resource(name="publishMasterConsumter")
    private PublishMasterConsumer ServerConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init();
        this.start();
        logger.info("server node starts");
    }

}
