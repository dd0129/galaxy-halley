package com.dianping.data.warehouse.halley.domain;

import java.io.Serializable;

/**
 * Created by mt on 2014/5/20.
 */
public class InstanceChildrenDO implements Serializable{
    private static final long serialVersionUID = 7332231310456881267L;
    private String id;
    private String pId;
    private String taskName;
    private String taskId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
