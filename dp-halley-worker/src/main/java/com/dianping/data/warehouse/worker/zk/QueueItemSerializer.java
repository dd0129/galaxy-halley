package com.dianping.data.warehouse.worker.zk;

import org.apache.commons.lang.SerializationUtils;
import org.apache.curator.framework.recipes.queue.QueueSerializer;

import java.io.Serializable;

/**
 * ZooKeeper队列对象序列化类
 */
public class QueueItemSerializer<T extends Serializable> implements QueueSerializer<T> {

    @Override
    public byte[] serialize(T t) {
        return SerializationUtils.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) {
        return (T) SerializationUtils.deserialize(bytes);
    }

}