package com.dianping.data.warehouse.dao;

import com.dianping.data.warehouse.domain.TaskDO;
import com.dianping.data.warehouse.domain.TaskRelaDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by adima on 14-3-22.
 */
public interface TaskDAO {
    public List<TaskDO> getValidateTaskList(@Param("status")Integer status);

    public List<TaskRelaDO> getTaskRelaList();

}
