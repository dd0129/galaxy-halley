package com.dianping.data.warehouse.halley.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class InstanceDisplayDO implements Serializable, Cloneable {

    private String cycle;
    private String endTime;
    private String logPath;
    private String owner;
    private Integer recallNum;
    private Integer runNum;
    private String startTime;
    private Integer status;
    private String taskName;
    private String taskStatusId;
    private Integer taskId;
    private String timeId;
    private Long triggerTime;
    private String databaseSrc;
    private Integer prioLvl;

    private List<String> parents;
    private List<String> children;

    private boolean isTarget;


    public Long getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Long triggerTime) {
        this.triggerTime = triggerTime;
    }

    public List<String> getChildren() {
        if (children == null)
            children = new ArrayList<String>();
        return children;
    }

    public String getCycle() {
        return cycle;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLogPath() {
        return logPath;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getParents() {
        if (parents == null)
            parents = new ArrayList<String>();
        return parents;
    }

    public Integer getRecallNum() {
        return recallNum;
    }

    public Integer getRunNum() {
        return runNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskStatusId() {
        return taskStatusId;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public void setRecallNum(Integer recallNum) {
        this.recallNum = recallNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskStatusId(String taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public Integer getPrioLvl() {
        return prioLvl;
    }

    public void setPrioLvl(Integer prioLvl) {
        this.prioLvl = prioLvl;
    }

    public String getDatabaseSrc() {
        return databaseSrc;
    }

    public void setDatabaseSrc(String databaseSrc) {
        this.databaseSrc = databaseSrc;
    }

    public String toString(int flag) {
        StringBuffer message = new StringBuffer();
        message.append("{");
        message.append("\"id\": \"").append(taskStatusId).append("\", ");
        message.append("\"status\": ").append(status == null ? "" : status).append(", ");
        if (cycle.equals("H")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(triggerTime);
            message.append("\"text\": \"").append(taskId).append(".").append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append("\", ");
        } else {
            if (flag == 2)
                message.append("\"text\": \"").append(taskId).append(".").append(timeId).append("\", ");
            else
                message.append("\"text\": \"").append(taskId).append("\", ");
        }
        if (children != null && children.size() > 0) {
            message.append("\"children\": [");
            for (String child : children)
                message.append("\"").append(child).append("\"").append(",");
            message.replace(message.length() - 1, message.length(), "]");
            message.append(", ");
        } else {
            message.append("\"children\": [], ");
        }
        if (parents != null && parents.size() > 0) {
            message.append("\"parents\": [");
            for (String parent : parents)
                message.append("\"").append(parent).append("\"").append(",");
            message.replace(message.length() - 1, message.length(), "]");
            message.append(", ");
        } else {
            message.append("\"parents\": [], ");
        }
        message.append("\"isTarget\": ").append(isTarget ? 1 : 0).append("}");
        return message.toString();
    }

    /**
     * 检查instanceDisplayDO是否符合instanceQueryDO的条件
     */
    public boolean fitQuery(InstanceQueryDO instanceQueryDO) {
        HashSet<Integer> taskIdSet = new HashSet<Integer>();
        if (instanceQueryDO.getTaskIds() != null) {
            for (String id : instanceQueryDO.getTaskIds())
                taskIdSet.add(Integer.valueOf(id));
        }
        if (instanceQueryDO.getCycle() != null && !instanceQueryDO.getCycle().equals(cycle))
            return false;
        if (instanceQueryDO.getStatus() == 99 && status == 1)
            return false;
        if (instanceQueryDO.getStatus() != 99 && instanceQueryDO.getStatus() != 100 &&
                !instanceQueryDO.getStatus().equals(status))
            return false;
        if (instanceQueryDO.getPrioLvl() == 4 && prioLvl < 4)
            return false;
        if (instanceQueryDO.getPrioLvl() != 4 && instanceQueryDO.getPrioLvl() != 100 &&
                !instanceQueryDO.getPrioLvl().equals(prioLvl))
            return false;
        if (instanceQueryDO.getTaskName() != null && taskName.indexOf(instanceQueryDO.getTaskName()) == -1)
            return false;
        if (instanceQueryDO.getTaskIds() != null && !taskIdSet.contains(taskId))
            return false;
        if (instanceQueryDO.getOwner() != null && !instanceQueryDO.getOwner().equals(owner))
            return false;
        return true;
    }

}
