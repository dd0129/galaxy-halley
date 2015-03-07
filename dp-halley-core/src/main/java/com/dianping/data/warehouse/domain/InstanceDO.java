package com.dianping.data.warehouse.domain;

import java.util.List;

public class InstanceDO implements Cloneable{
    private String instanceId;
    private Integer taskId;
    private Integer taskGroupId;
    private String taskName;
    private String taskObj;
    private Integer runningPrio;
    private String para1;
    private String para2;
    private String para3;
    private String logPath;
    private String cycle;
    private String timeId;
    private Integer status;
    private Integer prioLvl;
    private Integer runNum;
    private Integer type;
    private String databaseSrc;
    private String tableName;
    private String freq;
    private String calDt;
    private Integer ifPre;
    private String stsDesc;
    private Integer recallNum;
    private String owner;
    private Long TriggerTime;
    private String recallCode;
    private String successCode;
    private Integer ifWait;
    private Integer ifRecall;
    private String waitCode;
    private Integer timeout;
    private Integer recallLimit;
    private Integer recallInterval;
    private Long inQueueTimeMillis;
    private List<InstanceRelaDO> instRelaList;
    private String startTime;
    private String endTime;
    private String timestamp;
    private Integer jobCode;
    private String jobCodeMsg;

    public String getJobCodeMsg() {
        return jobCodeMsg;
    }

    public void setJobCodeMsg(String jobCodeMsg) {
        this.jobCodeMsg = jobCodeMsg;
    }

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

    public Integer getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(Integer taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskObj() {
        return taskObj;
    }

    public void setTaskObj(String taskObj) {
        this.taskObj = taskObj;
    }

    public Integer getRunningPrio() {
        return runningPrio;
    }

    public void setRunningPrio(Integer runningPrio) {
        this.runningPrio = runningPrio;
    }

    public String getPara1() {
        return para1;
    }

    public void setPara1(String para1) {
        this.para1 = para1;
    }

    public String getPara2() {
        return para2;
    }

    public void setPara2(String para2) {
        this.para2 = para2;
    }

    public String getPara3() {
        return para3;
    }

    public void setPara3(String para3) {
        this.para3 = para3;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
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

    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDatabaseSrc() {
        return databaseSrc;
    }

    public void setDatabaseSrc(String databaseSrc) {
        this.databaseSrc = databaseSrc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getCalDt() {
        return calDt;
    }

    public void setCalDt(String calDt) {
        this.calDt = calDt;
    }

    public Integer getIfPre() {
        return ifPre;
    }

    public void setIfPre(Integer ifPre) {
        this.ifPre = ifPre;
    }

    public String getStsDesc() {
        return stsDesc;
    }

    public void setStsDesc(String stsDesc) {
        this.stsDesc = stsDesc;
    }

    public Integer getRecallNum() {
        return recallNum;
    }

    public void setRecallNum(Integer recallNum) {
        this.recallNum = recallNum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getTriggerTime() {
        return TriggerTime;
    }

    public void setTriggerTime(Long triggerTime) {
        TriggerTime = triggerTime;
    }

    public String getRecallCode() {
        return recallCode;
    }

    public void setRecallCode(String recallCode) {
        this.recallCode = recallCode;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public Integer getIfWait() {
        return ifWait;
    }

    public void setIfWait(Integer ifWait) {
        this.ifWait = ifWait;
    }

    public Integer getIfRecall() {
        return ifRecall;
    }

    public void setIfRecall(Integer ifRecall) {
        this.ifRecall = ifRecall;
    }

    public String getWaitCode() {
        return waitCode;
    }

    public void setWaitCode(String waitCode) {
        this.waitCode = waitCode;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRecallLimit() {
        return recallLimit;
    }

    public void setRecallLimit(Integer recallLimit) {
        this.recallLimit = recallLimit;
    }

    public Integer getRecallInterval() {
        return recallInterval;
    }

    public void setRecallInterval(Integer recallInterval) {
        this.recallInterval = recallInterval;
    }

    public Long getInQueueTimeMillis() {
        return inQueueTimeMillis;
    }

    public void setInQueueTimeMillis(Long inQueueTimeMillis) {
        this.inQueueTimeMillis = inQueueTimeMillis;
    }

    public List<InstanceRelaDO> getInstRelaList() {
        return instRelaList;
    }

    public void setInstRelaList(List<InstanceRelaDO> instRelaList) {
        this.instRelaList = instRelaList;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getJobCode() {
        return jobCode;
    }

    public void setJobCode(Integer jobCode) {
        this.jobCode = jobCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
