package com.dianping.data.warehouse.halley.domain;

/**
 * Created by mt on 2014/5/28.
 */
public class InstanceInfoForCascadeDO {
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    String taskName;
    String taskID;
}
