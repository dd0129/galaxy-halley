package com.dianping.data.warehouse.halley.service;

/**
 * Created by hongdi.tang on 14-4-3.
 */
public interface TaskProducer {
    public boolean publish(String msg);
}
