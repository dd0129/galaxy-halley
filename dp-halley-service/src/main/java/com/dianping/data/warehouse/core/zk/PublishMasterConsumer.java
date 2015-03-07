package com.dianping.data.warehouse.core.zk;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;
import org.springframework.stereotype.Service;

@Service("publishMasterConsumter")
public class PublishMasterConsumer implements QueueConsumer<PublishFileDO> {

    @Override
	public void consumeMessage(PublishFileDO publishFile){

	}


    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

    }

}