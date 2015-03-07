package com.dianping.data.warehouse.domain;

import java.io.Serializable;

public class TaskRelaDO implements Serializable{
    private String cycleGap;
    private String remark;
    private Integer taskId;
    private Integer preId;
    private String timeStamp;

    public String getCycleGap() {
        return cycleGap;
    }

    public void setCycleGap(String cycleGap) {
        this.cycleGap = cycleGap;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPreId() {
        return preId;
    }

    public void setPreId(Integer preId) {
        this.preId = preId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}