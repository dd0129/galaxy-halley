package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.halley.domain.MonitorDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sunny on 14-8-12.
 */

@Service
public interface MonitorDAO {

    /**
     * 获得所有的值班人员的列表
     */
    public List<MonitorDO> getMonitors();

    /**
     * 插入一条新的值班记录
     */
    public Integer addMonitorRecord(MonitorDO monitorDO);
}
