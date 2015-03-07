package com.dianping.data.warehouse.halley.domain;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mt on 2014/5/9.
 */
public class TaskQueryDO implements Serializable {
    private Integer group;
    private String cycle;
    private String developer;
    private List<String> taskIds;
    private String taskName;

    public TaskQueryDO() {
    }

    public TaskQueryDO(Integer group, String cycle, String developer, String nameOrId) {
        this.group = group;
        this.cycle = cycle;
        this.developer = developer;
        this.taskIds = null;
        this.taskName = null;
        if (!StringUtils.isBlank(nameOrId)) {
            if (isTaskName(nameOrId)) {
                this.taskName = nameOrId;
            } else {
                String[] ids = nameOrId.split(",");
                this.taskIds = Arrays.asList(ids);
                //指定id下其他搜索条件默认为不限
                this.cycle = null;
                this.developer = null;
                this.group = 0;
            }
        }
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 判断传入参数是taskName还是taskIds
     */
    private boolean isTaskName(String taskNameOrId) {
        if (taskNameOrId.indexOf(',') != -1)
            return false;
        for (int i = 0; i < taskNameOrId.length(); i++)
            if (taskNameOrId.charAt(i) < '0' || taskNameOrId.charAt(i) > '9')
                return true;
        return false;
    }
}
