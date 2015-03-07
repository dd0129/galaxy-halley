package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.halley.domain.InstanceDisplayDO;

import com.dianping.data.warehouse.halley.domain.InstanceDO;
import com.dianping.data.warehouse.halley.domain.InstanceQueryDO;
import com.dianping.data.warehouse.halley.domain.TaskChildToParentDO;
import com.dianping.data.warehouse.halley.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunny on 14-5-14.
 */
@Service
public interface InstanceDAO {

    /**
     * 根据taskStatusIds获得其所有直接子节点的taskStatusId
     * 请在调用前确保taskStatusIds不为null且不为空
     */
    public List<String> getChildTaskStatusIdsByTaskStatusIds(List<String> taskStatusIds);

    /**
     * 根据instanceDO获得所有符合条件的taskStatusId
     * 请在调用前确保instanceQueryDO不为null
     */
    public List<String> getTaskStatusIdsByInstanceQueryDO(InstanceQueryDO instanceQueryDO);

    /**
     * 根据日期获得该日期下的所有的instances
     */
    public List<InstanceDisplayDO> getInstancesByDate(String date);

    /**
     * 根据查询条件获得所有符合条件的instances
     */
    public List<InstanceDisplayDO> getInstancesByInstanceQueryDO(InstanceQueryDO instanceQueryDO);

    /**
     * 根据instanceId获得其对应的instance
     */
    public InstanceDisplayDO getInstanceByTaskStatusId(String taskStatusId);

    /**
     * 获得与instanceId直接相关的instance
     */
    public List<Map<String, Object>> getInstanceDirectRelationsByInstanceId(String taskStatusId);

    /**
     * 根据taskStatusIds获得所有对应的Instance
     * 请在调用前确保taskStatusIds不为null且不为空
     */
    public List<InstanceDisplayDO> getInstancesByTaskStatusIds(List<String> taskStatusIds);

    /**
     * 根据taskStatusId获得所有直接父节点和儿子节点的taskStatusId列表
     */
    public List<String> getDirectRelationTaskStatusIdsByTaskStatusId(String taskStatusId);

    /**
     * 根据taskStatusIds获得所有的父子关系
     * 请在调用前确保taskStatusIds不为null且不为空
     */
    public List<Map<String, Object>> getInstanceRelationsByTaskStatusIds(List<String> taskStatusIds);

    /**
     * 任务重跑，将任务状态置为init
     */
    public int recall(String taskStatusId);

    /**
     * 置为挂起，将任务状态置为suspend
     */
    public int suspend(String taskStatusId);

    /**
     * 置为成功，将任务状态置为success
     */
    public int success(String taskStatusId);

    /**
     * 停止预跑，将任务状态置为挂起
     */
    public int batchStop(String taskId);

    /**
     * 快速通道，将任务的running_prio置为400
     */
    public int raisePriority(String taskStatusId);

    /**
     * 根据条件查询所有预跑任务
     */
    public List<HashMap<String, Object>> queryPreRunInstances(@Param("preRunTime") String preRunTime, @Param("task_committer") String task_committer,
                                                              @Param("taskID") Integer taskID, @Param("taskName") String taskName);

    public List<HashMap<String, Object>> getTaskStatusFieldForCascade(@Param("date") String date);

    public List<HashMap<String, Object>> getTaskRelaStatusFieldForCascade(@Param("date") String date);

    public List<TaskChildToParentDO> getChildToParent(@Param("date") String date);

    /**
     * 级联重跑相关instance
     */
    Integer reRunCascadeJobs(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("taskIds") List<String> taskIds);

    /**
     * 插入一条instance记录
     */
    public void insertInstance(InstanceDO instanceDO);

    public InstanceDO getInstancesForOncall(@Param("taskId") Integer taskId, @Param("timeId") String timeId);

    public String getExceptionResponsible(@Param("jobCode") Integer jobCode);

    public TaskOwnerDO getOwnerByConditions(@Param("owner") String owner, @Param("groupType") String groupType);

    /**
     * 根据instanceId获得其直接前置instanceIds
     */
    public List<String> getDirectPreInstanceIdsByInstanceId(String taskStatusId);

    /**
     * 获得处于status状态的任务实例
     */
    public List<InstanceDisplayDO> getInstancesByStatus(int status);


    public InstanceDO getInstanceForOncallByTaskStatusId(String taskStatusId);

}
