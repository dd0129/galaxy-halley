package com.dianping.data.warehouse.halley.dao;


import com.dianping.data.warehouse.halley.model.InstanceDO;
import com.dianping.data.warehouse.halley.model.TransformLogDO;

import java.util.List;

/**
 * Created by adima on 14-3-23.
 */
public interface InstanceDAO {

    public List<InstanceDO> getInstances(String timeId);

    public void insertLog(TransformLogDO log);

    public void deleteLog(String instanceId);

}
