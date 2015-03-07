package com.dianping.data.warehouse.halley.domain;


import com.dianping.data.warehouse.halley.client.Const;
import com.dianping.data.warehouse.halley.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class TaskDO implements Serializable, Cloneable {

    private static final long serialVersionUID = -2434844654316708446L;

    private String addTime;
    @NotBlank(message = "addUser is not null")
    private String addUser;
    @NotBlank(message = "cycle is not null")
    private String cycle;
    @NotBlank(message = "databaseSrc is not null")
    private String databaseSrc;
    @NotBlank(message = "freq is not null")
    private String freq;
    @NotNull(message = "ifPre is not null")
    private Integer ifPre;
    @NotNull(message = "ifRecall is not null")
    private Integer ifRecall;
    @NotNull(message = "ifVal is not null")
    private Integer ifVal;
    @NotNull(message = "ifWait is not null")
    private Integer ifWait;
    @NotBlank(message = "logFile is not null")
    private String logFile;
    @NotBlank(message = "logHome is not null")
    private String logHome;
    @NotBlank(message = "offset is not null")
    private String offset;
    @NotBlank(message = "offsetType is not null")
    private String offsetType;
    @NotBlank(message = "owner is not null")
    private String owner;
    @NotBlank(message = "para1 is not null")
    private String para1;
    private String para2;
    private String para3;

    @NotNull(message = "prioLvl is not null")
    private Integer prioLvl;
    private String recallCode;
    @NotNull(message = "recallInterval is not null")
    private Integer recallInterval;
    private Integer recallLimit;
    private String remark;
    @NotBlank(message = "successCode is not null")
    private String successCode;
    @NotBlank(message = "tableName is not null")
    private String tableName;
    @NotNull(message = "taskGroupId is not null")
    private Integer taskGroupId;
    @NotNull(message = "taskId is not null")
    private Integer taskId;
    @NotBlank(message = "taskName is not null")
    private String taskName;
    @NotBlank(message = "taskObj is not null")
    private String taskObj;
    @NotNull(message = "timeout is not null")
    private Integer timeout;
    @NotNull(message = "type is not null")
    private Integer type;
    private String updateTime;
    @NotBlank(message = "updateUser is not null")
    private String updateUser;
    private String waitCode;
    private List<TaskRelaDO> relaDOList;

    private Integer hasTargetTable;

    public List<TaskRelaDO> getRelaDOList() {
        return relaDOList;
    }

    public void setRelaDOList(List<TaskRelaDO> relaDOList) {
        this.relaDOList = relaDOList;
    }

    public TaskDO(Integer taskGroupId, String databaseSrc) {
        this.taskGroupId = taskGroupId;
        this.databaseSrc = databaseSrc;
    }

    public TaskDO() {
    }

    public String getAddTime() {
        return addTime;
    }

    public String getAddUser() {
        return addUser;
    }

    public String getCycle() {
        return cycle;
    }

    public String getDatabaseSrc() {
        return databaseSrc;
    }

    public String getFreq() {
        return freq;
    }

    public Integer getIfPre() {
        return ifPre;
    }

    public Integer getIfRecall() {
        return ifRecall;
    }

    public Integer getIfVal() {
        return ifVal;
    }

    public Integer getIfWait() {
        return ifWait;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getLogHome() {
        return logHome;
    }

    public String getOffset() {
        return offset;
    }

    public String getOffsetType() {
        return offsetType;
    }

    public String getOwner() {
        return owner;
    }

    public String getPara1() {
        return para1;
    }

    public String getPara2() {
        return para2;
    }

    public String getPara3() {
        return para3;
    }

    public Integer getPrioLvl() {
        return prioLvl;
    }

    public String getRecallCode() {
        return recallCode;
    }

    public Integer getRecallInterval() {
        return recallInterval;
    }

    public Integer getRecallLimit() {
        return recallLimit;
    }

    public String getRemark() {
        return remark;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public String getTableName() {
        return tableName;
    }

    public Integer getTaskGroupId() {
        return taskGroupId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskObj() {
        return taskObj;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public Integer getType() {
        return type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public String getWaitCode() {
        return waitCode;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public void setDatabaseSrc(String databaseSrc) {
        this.databaseSrc = databaseSrc;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public void setIfPre(Integer ifPre) {
        this.ifPre = ifPre;
    }

    public void setIfRecall(Integer ifRecall) {
        this.ifRecall = ifRecall;
    }

    public void setIfVal(Integer ifVal) {
        this.ifVal = ifVal;
    }

    public void setIfWait(Integer ifWait) {
        this.ifWait = ifWait;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public void setLogHome(String logHome) {
        this.logHome = logHome;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setOffsetType(String offsetType) {
        this.offsetType = offsetType;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPara1(String para1) {
        this.para1 = para1;
    }


    public void setPara2(String para2) {
        this.para2 = para2;
    }

    public void setPara3(String para3) {
        this.para3 = para3;
    }

    public void setPrioLvl(Integer prioLvl) {
        this.prioLvl = prioLvl;
    }

    public void setRecallCode(String recallCode) {
        this.recallCode = recallCode;
    }

    public void setRecallInterval(Integer recallInterval) {
        this.recallInterval = recallInterval;
    }

    public void setRecallLimit(Integer recallLimit) {
        this.recallLimit = recallLimit;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTaskGroupId(Integer taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskObj(String taskObj) {
        this.taskObj = taskObj;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setWaitCode(String waitCode) {
        this.waitCode = waitCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addTime == null) ? 0 : addTime.hashCode());
        result = prime * result + ((addUser == null) ? 0 : addUser.hashCode());
        result = prime * result + ((cycle == null) ? 0 : cycle.hashCode());
        result = prime * result
                + ((databaseSrc == null) ? 0 : databaseSrc.hashCode());
        result = prime * result + ((freq == null) ? 0 : freq.hashCode());
        result = prime * result + ((ifPre == null) ? 0 : ifPre.hashCode());
        result = prime * result
                + ((ifRecall == null) ? 0 : ifRecall.hashCode());
        result = prime * result + ((ifVal == null) ? 0 : ifVal.hashCode());
        result = prime * result + ((ifWait == null) ? 0 : ifWait.hashCode());
        result = prime * result + ((logFile == null) ? 0 : logFile.hashCode());
        result = prime * result + ((logHome == null) ? 0 : logHome.hashCode());
        result = prime * result + ((offset == null) ? 0 : offset.hashCode());
        result = prime * result
                + ((offsetType == null) ? 0 : offsetType.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((para1 == null) ? 0 : para1.hashCode());
        result = prime * result + ((para2 == null) ? 0 : para2.hashCode());
        result = prime * result + ((para3 == null) ? 0 : para3.hashCode());
        result = prime * result + ((prioLvl == null) ? 0 : prioLvl.hashCode());
        result = prime * result
                + ((recallCode == null) ? 0 : recallCode.hashCode());
        result = prime * result
                + ((recallInterval == null) ? 0 : recallInterval.hashCode());
        result = prime * result
                + ((recallLimit == null) ? 0 : recallLimit.hashCode());
        result = prime * result
                + ((relaDOList == null) ? 0 : relaDOList.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result
                + ((successCode == null) ? 0 : successCode.hashCode());
        result = prime * result
                + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result
                + ((taskGroupId == null) ? 0 : taskGroupId.hashCode());
        result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
        result = prime * result
                + ((taskName == null) ? 0 : taskName.hashCode());
        result = prime * result + ((taskObj == null) ? 0 : taskObj.hashCode());
        result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result
                + ((updateTime == null) ? 0 : updateTime.hashCode());
        result = prime * result
                + ((updateUser == null) ? 0 : updateUser.hashCode());
        result = prime * result
                + ((waitCode == null) ? 0 : waitCode.hashCode());
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
        TaskDO other = (TaskDO) obj;
        if (addTime == null) {
            if (other.addTime != null)
                return false;
        } else if (!addTime.equals(other.addTime))
            return false;
        if (addUser == null) {
            if (other.addUser != null)
                return false;
        } else if (!addUser.equals(other.addUser))
            return false;
        if (cycle == null) {
            if (other.cycle != null)
                return false;
        } else if (!cycle.equals(other.cycle))
            return false;
        if (databaseSrc == null) {
            if (other.databaseSrc != null)
                return false;
        } else if (!databaseSrc.equals(other.databaseSrc))
            return false;
        if (freq == null) {
            if (other.freq != null)
                return false;
        } else if (!freq.equals(other.freq))
            return false;
        if (ifPre == null) {
            if (other.ifPre != null)
                return false;
        } else if (!ifPre.equals(other.ifPre))
            return false;
        if (ifRecall == null) {
            if (other.ifRecall != null)
                return false;
        } else if (!ifRecall.equals(other.ifRecall))
            return false;
        if (ifVal == null) {
            if (other.ifVal != null)
                return false;
        } else if (!ifVal.equals(other.ifVal))
            return false;
        if (ifWait == null) {
            if (other.ifWait != null)
                return false;
        } else if (!ifWait.equals(other.ifWait))
            return false;
        if (logFile == null) {
            if (other.logFile != null)
                return false;
        } else if (!logFile.equals(other.logFile))
            return false;
        if (logHome == null) {
            if (other.logHome != null)
                return false;
        } else if (!logHome.equals(other.logHome))
            return false;
        if (offset == null) {
            if (other.offset != null)
                return false;
        } else if (!offset.equals(other.offset))
            return false;
        if (offsetType == null) {
            if (other.offsetType != null)
                return false;
        } else if (!offsetType.equals(other.offsetType))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (para1 == null) {
            if (other.para1 != null)
                return false;
        } else if (!para1.equals(other.para1))
            return false;
        if (para2 == null) {
            if (other.para2 != null)
                return false;
        } else if (!para2.equals(other.para2))
            return false;
        if (para3 == null) {
            if (other.para3 != null)
                return false;
        } else if (!para3.equals(other.para3))
            return false;
        if (prioLvl == null) {
            if (other.prioLvl != null)
                return false;
        } else if (!prioLvl.equals(other.prioLvl))
            return false;
        if (recallCode == null) {
            if (other.recallCode != null)
                return false;
        } else if (!recallCode.equals(other.recallCode))
            return false;
        if (recallInterval == null) {
            if (other.recallInterval != null)
                return false;
        } else if (!recallInterval.equals(other.recallInterval))
            return false;
        if (recallLimit == null) {
            if (other.recallLimit != null)
                return false;
        } else if (!recallLimit.equals(other.recallLimit))
            return false;
        if (relaDOList == null) {
            if (other.relaDOList != null)
                return false;
        } else if (!relaDOList.equals(other.relaDOList))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (successCode == null) {
            if (other.successCode != null)
                return false;
        } else if (!successCode.equals(other.successCode))
            return false;
        if (tableName == null) {
            if (other.tableName != null)
                return false;
        } else if (!tableName.equals(other.tableName))
            return false;
        if (taskGroupId == null) {
            if (other.taskGroupId != null)
                return false;
        } else if (!taskGroupId.equals(other.taskGroupId))
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
        if (taskObj == null) {
            if (other.taskObj != null)
                return false;
        } else if (!taskObj.equals(other.taskObj))
            return false;
        if (timeout == null) {
            if (other.timeout != null)
                return false;
        } else if (!timeout.equals(other.timeout))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (updateTime == null) {
            if (other.updateTime != null)
                return false;
        } else if (!updateTime.equals(other.updateTime))
            return false;
        if (updateUser == null) {
            if (other.updateUser != null)
                return false;
        } else if (!updateUser.equals(other.updateUser))
            return false;
        if (waitCode == null) {
            if (other.waitCode != null)
                return false;
        } else if (!waitCode.equals(other.waitCode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaskDO [addTime=" + addTime + ", addUser=" + addUser
                + ", cycle=" + cycle + ", databaseSrc=" + databaseSrc
                + ", freq=" + freq + ", ifPre=" + ifPre + ", ifRecall="
                + ifRecall + ", ifVal=" + ifVal + ", ifWait=" + ifWait
                + ", logFile=" + logFile + ", logHome=" + logHome + ", offset="
                + offset + ", offsetType=" + offsetType + ", owner=" + owner
                + ", para1=" + para1 + ", para2=" + para2 + ", para3=" + para3
                + ", prioLvl=" + prioLvl + ", recallCode=" + recallCode
                + ", recallInterval=" + recallInterval + ", recallLimit="
                + recallLimit + ", remark=" + remark + ", successCode="
                + successCode + ", tableName=" + tableName + ", taskGroupId="
                + taskGroupId + ", taskId=" + taskId + ", taskName=" + taskName
                + ", taskObj=" + taskObj + ", timeout=" + timeout + ", type="
                + type + ", updateTime=" + updateTime + ", updateUser="
                + updateUser + ", waitCode=" + waitCode + ", relaDOList="
                + relaDOList + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getHasTargetTable() {
        return hasTargetTable;
    }

    public void setHasTargetTable(Integer hasTargetTable) {
        this.hasTargetTable = hasTargetTable;
    }


    public void mergeParas() {
        if (para2 != null) {
            para1 += " " + para2;
            para2 = null;
        }
        if (para3 != null) {
            para1 += " " + para3;
            para3 = null;
        }
    }

    /**
     * 任务更新时设置taskDO的值
     */
    public void setParametersForUpdate(String updateUser) {
        this.updateUser = updateUser;
        setDefaultParameterForUpdate();
        setTaskIdForTaskRelaDO();
    }

    /**
     * 更新任务时设置taskDO的默认值
     */
    private void setDefaultParameterForUpdate() {
        String nowTimeStamp = CommonUtils.getCurrentTimeStampStr();
        updateTime = nowTimeStamp;
        logFile = tableName;
        if (!StringUtils.isBlank(waitCode)) {
            ifWait = Const.IF_YES;
        } else {
            ifWait = Const.IF_NO;
            waitCode = null;
        }
        if (relaDOList == null || relaDOList.isEmpty())
            ifPre = Const.IF_NO;
        else
            ifPre = Const.IF_YES;
    }

    /**
     * 设置taskDO中的relaDOList的taskId
     */
    public void setTaskIdForTaskRelaDO() {
        for (TaskRelaDO taskRelaDO : relaDOList) {
            taskRelaDO.setTaskId(taskId);
        }
    }

    /**
     * 任务新增时设置taskDO的值
     */
    public void setParametersForAdd(String addUser) {
        updateUser = addUser;
        this.addUser = addUser;
        setDefaultParameterForAdd();
    }

    /**
     * 新增任务时设置taskDO的默认值
     */
    private void setDefaultParameterForAdd() {
        String nowTimeStamp = CommonUtils.getCurrentTimeStampStr();
        addTime = nowTimeStamp;
        updateTime = nowTimeStamp;
        logFile = tableName;
        if (!StringUtils.isBlank(waitCode)) {
            ifWait = Const.IF_YES;
        } else {
            ifWait = Const.IF_NO;
            waitCode = null;
        }
        if (relaDOList == null || relaDOList.isEmpty())
            ifPre = Const.IF_NO;
        else
            ifPre = Const.IF_YES;
        databaseSrc = Const.TASK_DATEBASE_SRC;
        logHome = Const.TASK_LOG_HOME;
        offsetType = Const.TASK_OFFSET_TYPE;
        recallCode = null;
        remark = null;
        taskObj = Const.TASK_OBJ;
        type = Const.TASK_TYPE_CALCULATE; //计算任务
        para2 = null;
        para3 = null;
    }

}
