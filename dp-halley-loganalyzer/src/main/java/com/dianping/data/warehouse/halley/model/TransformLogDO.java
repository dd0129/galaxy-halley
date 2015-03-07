package com.dianping.data.warehouse.halley.model;

/**
 * Created by hongdi.tang on 2015/1/14.
 */
public class TransformLogDO {
    private String instanceId;
    private Integer taskId;
    private String startTime;
    private String endTime;
    private String timeId;
    private Double timeCost;
    private String avgByteSpeed;
    private Double avgLineSpeed;
    private Double totalRecords;
    private String host;
    private String logPath;
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

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public Double getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Double timeCost) {
        this.timeCost = timeCost;
    }


    public Double getAvgLineSpeed() {
        return avgLineSpeed;
    }

    public void setAvgLineSpeed(Double avgLineSpeed) {
        this.avgLineSpeed = avgLineSpeed;
    }

    public Double getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Double totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAvgByteSpeed() {
        return avgByteSpeed;
    }

    public void setAvgByteSpeed(String avgByteSpeed) {
        this.avgByteSpeed = avgByteSpeed;
    }
}
