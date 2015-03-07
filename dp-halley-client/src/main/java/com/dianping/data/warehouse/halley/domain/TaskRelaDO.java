package com.dianping.data.warehouse.halley.domain;

import java.io.Serializable;

public class TaskRelaDO implements Serializable {

    private static final long serialVersionUID = -1827386386229460099L;
    private String cycleGap;
    private String remark;
    private Integer taskId;
    private Integer taskPreId;
    private String taskName;
    private String timeStamp;
    private String owner;

    public TaskRelaDO() {
    }

    public TaskRelaDO(Integer taskPreId, Integer taskId, String taskName) {
        this.taskPreId = taskPreId;
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getCycleGap() {
        return cycleGap;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Integer getTaskPreId() {
        return taskPreId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setCycleGap(String cycleGap) {
        this.cycleGap = cycleGap;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void setTaskPreId(Integer taskPreId) {
        this.taskPreId = taskPreId;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((cycleGap == null) ? 0 : cycleGap.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
        result = prime * result
                + ((taskName == null) ? 0 : taskName.hashCode());
        result = prime * result
                + ((taskPreId == null) ? 0 : taskPreId.hashCode());
        result = prime * result
                + ((timeStamp == null) ? 0 : timeStamp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskRelaDO other = (TaskRelaDO) obj;
        if (cycleGap == null) {
            if (other.cycleGap != null)
                return false;
        } else if (!cycleGap.equals(other.cycleGap))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (taskId == null) {
            if (other.taskId != null)
                return false;
        } else if (!taskId.equals(other.taskId))
            return false;
        if (taskName == null) {
            if (other.taskName != null)
                return false;
        } else if (!taskName.equals(other.taskName))
            return false;
        if (taskPreId == null) {
            if (other.taskPreId != null)
                return false;
        } else if (!taskPreId.equals(other.taskPreId))
            return false;
        if (timeStamp == null) {
            if (other.timeStamp != null)
                return false;
        } else if (!timeStamp.equals(other.timeStamp))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaskRelaDO [cycleGap=" + cycleGap + ", remark=" + remark
                + ", taskId=" + taskId + ", taskPreId=" + taskPreId
                + ", taskName=" + taskName + ", timeStamp=" + timeStamp
                + ", owner=" + owner + "]";
    }


}
