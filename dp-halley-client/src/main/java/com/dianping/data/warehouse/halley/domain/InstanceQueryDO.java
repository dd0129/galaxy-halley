package com.dianping.data.warehouse.halley.domain;

import com.dianping.data.warehouse.halley.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sunny on 14-5-14.
 */
public class InstanceQueryDO implements Serializable, Cloneable {
    private String cycle;
    private Integer status;
    private Integer prioLvl;
    private String owner;
    private boolean dependencyIsShow;
    private String startTime;
    private String endTime;
    private List<String> taskIds;
    private String taskName;

    public InstanceQueryDO() {
    }

    public InstanceQueryDO(String startDate, String endDate, String executionCycle, String developer, String executionStatus,
                           String executionPriority, String dependencyIsShowOptions, String taskNameOrId) {
        String currentDate = CommonUtils.getCurrentDateStr();
        List<String> taskIds = null;
        String taskName = null;
        boolean dependencyIsShow = true;
        int status = Integer.parseInt(executionStatus);
        int priority = Integer.parseInt(executionPriority);
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            startDate = currentDate;
            endDate = currentDate;
        }
        if (StringUtils.isBlank(developer))
            developer = null;
        if (!StringUtils.isBlank(taskNameOrId)) {
            if (isTaskName(taskNameOrId)) {
                taskName = taskNameOrId;
            } else {
                String[] taskNameOrIds = taskNameOrId.split(",");
                taskIds = Arrays.asList(taskNameOrIds);
                //指定id下其他搜索条件默认为不限
                executionCycle = null;
                developer = null;
                priority = 100;
            }
        }
        if (StringUtils.isBlank(executionCycle))
            executionCycle = null;
        if (dependencyIsShowOptions.equals("0"))
            dependencyIsShow = false;

        this.startTime = startDate;
        this.endTime = endDate;
        this.cycle = executionCycle;
        this.owner = developer;
        this.dependencyIsShow = dependencyIsShow;
        this.status = status;
        this.prioLvl = priority;
        this.taskIds = taskIds;
        this.taskName = taskName;
    }

    /**
     * 判断传入参数是taskName还是taskIds
     */
    private boolean isTaskName(String taskNameOrId) {
        if (taskNameOrId.indexOf(',') != -1)
            return false;
        for (int i = 0; i < taskNameOrId.length(); i++)
            if (taskNameOrId.charAt(i) < '0' || taskNameOrId.charAt(i) > '9')
                return true;
        return false;
    }


    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrioLvl() {
        return prioLvl;
    }

    public void setPrioLvl(Integer prioLvl) {
        this.prioLvl = prioLvl;
    }

    public boolean getDependencyIsShow() {
        return dependencyIsShow;
    }

    public void setDependencyIsShow(boolean dependencyIsShow) {
        this.dependencyIsShow = dependencyIsShow;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}


