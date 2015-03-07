package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.halley.domain.InstanceRelaDO;
import com.dianping.data.warehouse.halley.domain.TaskDO;
import com.dianping.data.warehouse.halley.domain.TaskQueryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskDAO {

    /**
     * 通过taskId获得task
     */
    public TaskDO getTaskByTaskId(int id);

    /**
     * 根据条件查询task
     */
    public List<TaskDO> queryTasksByTaskQueryDO(TaskQueryDO taskQueryDO);

    /**
     * 获得所有的taskId
     */
    public List<Integer> getTaskIDList();

    /**
     * 插入task
     */
    public void insertTask(TaskDO taskDO);

    /**
     * 插入实例依赖
     */
    public void insertInstanceRela(InstanceRelaDO relaDO);

    /**
     * 更新task
     */
    public void updateTask(TaskDO taskDO);

    /**
     * 更新taskId对应task的状态为status
     */
    public void updateTaskStatus(@Param("taskId") int taskId, @Param("status") int status,
                                 @Param("updateTime") String updateTime, @Param("updateUser") String updateUser);

    /**
     * 删除task
     */
    public void deleteTask(@Param("taskId") int taskId, @Param("updateTime") String updateTime,
                           @Param("updateUser") String updateUser);

    /**
     * 获得taskName对应的taskId，不考虑废弃任务
     */
    public Integer getTaskIdByTaskName(String taskName);

    /**
     * 根据taskIds获得上线的tasks
     */
    public List<TaskDO> getValidTasksByTaskIds(List<Integer> taskIds);

    /**
     * 获得所有生效的tasks
     */
    public List<TaskDO> getAllValidTasks();

    /**
     * 获得所有的周期为天的生效任务
     */
    public List<TaskDO> getDayTasks();

    public List<Integer> getParentTaskIdList(List<String> taskParentTableList);

    public List<Map<String, Integer>> getParentTaskIdsByTables(List<String> tables);
}
