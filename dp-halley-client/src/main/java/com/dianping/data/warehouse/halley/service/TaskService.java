package com.dianping.data.warehouse.halley.service;

import com.dianping.data.warehouse.halley.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-2-11.
 */
public interface TaskService {

    /**
     * 根据taskId获得task
     */
    public TaskDO getTaskByTaskId(Integer id);

    /**
     * 根据条件查询task
     */
    public List<TaskDO> queryTasks(TaskQueryDO taskQueryDO);

    /**
     * 预跑任务
     */
    public boolean preRunTask(Integer taskId, String begin, String end, String committer);

    /**
     * 删除任务
     */
    public boolean deleteTaskByTaskId(Integer id, String updateTime, String updateUser);

    /**
     * 新增任务
     */
    public boolean insertTask(TaskDO task);

    /**
     * 更新任务
     */
    public boolean updateTask(TaskDO task);

    /**
     * 检测是否存在环依赖
     */
    public boolean hasCycleDependence(TaskDO task);

    /**
     * 生效任务
     */
    public boolean validTaskByTaskId(Integer id, String updateTime, String updateUser);

    /**
     * 失效任务
     */
    public boolean invalidTaskByTaskId(Integer id, String updateTime, String updateUser);

    /**
     * 生成taskId
     */
    public Integer generateTaskID(TaskDO task);

    /**
     * 检测指定task name是否存在
     */
    public boolean taskNameExists(String taskName);

    /**
     * 根据taskId获得其所有后续任务
     * 返回后续任务和自身所组成的list
     */
    public List<TaskDO> getPostTasksByTaskId(Integer taskId);

    /**
     * 获取级联重跑所有需要重跑的instance
     */
    public List<TaskRelaDO> getTasksForCascadePreRun(Integer taskId);

    /**
     * 级联预跑
     */
    public boolean cascadePreRun(String startDate, String endDate, List<String> taskIds, String committer);

    public boolean preRunJobAscend(Integer taskId, String begin, String end, String task_committer);

    public List<Map<String, Integer>> getTaskIdsByTable(List<String> tables);

    public List<Integer> getParentTaskIdList(List<String> tableNames);

    public List<TaskDO> getValidTasksByTaskIds(List<Integer> taskIds);


}
