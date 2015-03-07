package com.dianping.data.warehouse.domain;

public class TaskDO {
    private Integer taskId        ;
    private String taskName       ;
    private String tableName      ;
    private String remark         ;
    private String databaseSrc    ;
    private String taskObj        ;
    private String para1          ;
    private String para2          ;
    private String para3          ;
    private String logHome        ;
    private String logFile        ;
    private Integer taskGroupId   ;
    private String cycle          ;
    private Integer prioLvl       ;
    private Integer ifRecall      ;
    private Integer ifWait        ;
    private Integer ifPre         ;
    private Integer ifVal         ;
    private String addUser        ;
    private String addTime        ;
    private String updateUser     ;
    private String updateTime     ;
    private Integer type          ;
    private String offset         ;
    private String offsetType     ;
    private String freq           ;
    private String owner          ;
    private String waitCode       ;
    private String recallCode     ;
    private Integer timeout       ;
    private Integer recallInterval;
    private Integer recallLimit   ;
    private String successCode    ;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDatabaseSrc() {
        return databaseSrc;
    }

    public void setDatabaseSrc(String databaseSrc) {
        this.databaseSrc = databaseSrc;
    }

    public String getTaskObj() {
        return taskObj;
    }

    public void setTaskObj(String taskObj) {
        this.taskObj = taskObj;
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

    public String getLogHome() {
        return logHome;
    }

    public void setLogHome(String logHome) {
        this.logHome = logHome;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public Integer getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(Integer taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getPrioLvl() {
        return prioLvl;
    }

    public void setPrioLvl(Integer prioLvl) {
        this.prioLvl = prioLvl;
    }

    public Integer getIfRecall() {
        return ifRecall;
    }

    public void setIfRecall(Integer ifRecall) {
        this.ifRecall = ifRecall;
    }

    public Integer getIfWait() {
        return ifWait;
    }

    public void setIfWait(Integer ifWait) {
        this.ifWait = ifWait;
    }

    public Integer getIfPre() {
        return ifPre;
    }

    public void setIfPre(Integer ifPre) {
        this.ifPre = ifPre;
    }

    public Integer getIfVal() {
        return ifVal;
    }

    public void setIfVal(Integer ifVal) {
        this.ifVal = ifVal;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getOffsetType() {
        return offsetType;
    }

    public void setOffsetType(String offsetType) {
        this.offsetType = offsetType;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWaitCode() {
        return waitCode;
    }

    public void setWaitCode(String waitCode) {
        this.waitCode = waitCode;
    }

    public String getRecallCode() {
        return recallCode;
    }

    public void setRecallCode(String recallCode) {
        this.recallCode = recallCode;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRecallInterval() {
        return recallInterval;
    }

    public void setRecallInterval(Integer recallInterval) {
        this.recallInterval = recallInterval;
    }

    public Integer getRecallLimit() {
        return recallLimit;
    }

    public void setRecallLimit(Integer recallLimit) {
        this.recallLimit = recallLimit;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }
}
