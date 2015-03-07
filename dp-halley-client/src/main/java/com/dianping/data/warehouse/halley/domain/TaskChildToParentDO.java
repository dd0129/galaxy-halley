package com.dianping.data.warehouse.halley.domain;

/**
 * Created by mt on 2014/5/19.
 */
public class TaskChildToParentDO {

    String childId;
    String childText;
    String childTaskName;
    Integer childStatus;
    Long childEndTime;
    String parentId;
    String parentText;
    String parentTaskName;
    Integer parentStatus;
    Long parentEndTime;
    String timeId;

    public Long getParentEndTime() {
        return parentEndTime;
    }

    public void setParentEndTime(Long parentEndTime) {
        this.parentEndTime = parentEndTime;
    }

    public Long getChildEndTime() {
        return childEndTime;
    }

    public void setChildEndTime(Long childEndTime) {
        this.childEndTime = childEndTime;
    }



    public String getChildId() {
        return childId;
    }
    public void setChildId(String childId) {
        this.childId = childId;
    }
    public String getChildTaskName() {
        return childTaskName;
    }
    public void setChildTaskName(String childTaskName) {
        this.childTaskName = childTaskName;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentTaskName() {
        return parentTaskName;
    }
    public void setParentTaskName(String parentTaskName) {
        this.parentTaskName = parentTaskName;
    }
    public String getTimeId() {
        return timeId;
    }
    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }
    public Integer getChildStatus() {
        return childStatus;
    }
    public void setChildStatus(Integer childStatus) {
        this.childStatus = childStatus;
    }
    public Integer getParentStatus() {
        return parentStatus;
    }
    public void setParentStatus(Integer parentStatus) {
        this.parentStatus = parentStatus;
    }
    public String getChildText() {
        return childText;
    }
    public void setChildText(String childText) {
        this.childText = childText;
    }
    public String getParentText() {
        return parentText;
    }
    public void setParentText(String parentText) {
        this.parentText = parentText;
    }

}
