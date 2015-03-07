package com.dianping.data.warehouse.domain;

import java.util.List;

/**
 * Created by adima on 14-3-22.
 */
public class InstanceRelaDO {
    private String instanceId;
    private Integer taskId;
    private String preInstanceId;
    private Integer preId;
    private String timestamp;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getPreInstanceId() {
        return preInstanceId;
    }

    public void setPreInstanceId(String preInstanceId) {
        this.preInstanceId = preInstanceId;
    }

    public Integer getPreId() {
        return preId;
    }

    public void setPreId(Integer preId) {
        this.preId = preId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
