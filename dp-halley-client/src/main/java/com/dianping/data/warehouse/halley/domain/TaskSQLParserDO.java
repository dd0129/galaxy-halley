package com.dianping.data.warehouse.halley.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sunny on 14-6-23.
 */
public class TaskSQLParserDO implements Serializable, Cloneable {
    private boolean isSuccess;

    private String message;

    private Integer launch;

    private List<String> parentTableNames;

    private List<String> childTableNames;

    private List<TaskRelaDO> parentTaskRelaDOs;

    private String dolPath;

    public String getDolPath() {
        return dolPath;
    }

    public void setDolPath(String dolPath) {
        this.dolPath = dolPath;
    }

    public TaskSQLParserDO() {
    }

    public List<String> getParentTableNames() {
        return parentTableNames;
    }

    public void setParentTableNames(List<String> parentTableNames) {
        this.parentTableNames = parentTableNames;
    }

    public List<String> getChildTableNames() {
        return childTableNames;
    }

    public void setChildTableNames(List<String> childTableNames) {
        this.childTableNames = childTableNames;
    }

    public List<TaskRelaDO> getParentTaskRelaDOs() {
        return parentTaskRelaDOs;
    }

    public void setParentTaskRelaDOs(List<TaskRelaDO> parentTaskRelaDOs) {
        this.parentTaskRelaDOs = parentTaskRelaDOs;
    }

    public void setLaunch(Integer launch) {
        this.launch = launch;
    }

    public Integer getLaunch() {
        return launch;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
