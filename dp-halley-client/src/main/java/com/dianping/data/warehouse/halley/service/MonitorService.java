package com.dianping.data.warehouse.halley.service;

import com.dianping.data.warehouse.halley.domain.MonitorDO;

import java.util.List;

/**
 * Created by Sunny on 14-8-12.
 */
public interface MonitorService {

    public int rollback(String gitPath, String filePath);

    /**
     * 获得所有的值班人员
     */
    public List<MonitorDO> getMonitors();

    /**
     * 在值班人员表中插入一条新纪录
     */
    public Integer addMonitorRecord(MonitorDO monitorDO);


    /**
     * 用户是否是管理员
     */
    public boolean isAdmin(int loginId);

    /**
     * 用户是否是值班人员
     */
    public boolean isMonitor(String pinyinName);

}
