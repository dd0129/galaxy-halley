package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.Const;
import com.dianping.data.warehouse.core.common.SqlParser.AnalyzeResult;
import com.dianping.data.warehouse.core.common.SqlParser.Parser;
import com.dianping.data.warehouse.core.dao.PublishFileDAO;
import com.dianping.data.warehouse.core.dao.TaskDAO;
import com.dianping.data.warehouse.core.hdfs.HdfsUtils;
import com.dianping.data.warehouse.core.jgit.JGitUtils;
import com.dianping.data.warehouse.core.lion.LionUtil;
import com.dianping.data.warehouse.core.util.HalleyStrUtils;
import com.dianping.data.warehouse.core.zk.ServerNode;
import com.dianping.data.warehouse.halley.domain.*;
import com.dianping.data.warehouse.halley.service.TaskPublish;
import com.dianping.pigeon.remoting.provider.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-6-9.
 */
@Service
public class TaskPublishImpl implements TaskPublish {

    private static Logger logger = LoggerFactory.getLogger(TaskPublishImpl.class);

    @Resource(name = "serverNode")
    private ServerNode serverNode;

    @Resource
    private TaskDAO taskDAO;

    @Resource
    private PublishFileDAO publishLogDAO;

    /**
     * 任务发布
     * 返回DOL解析结果
     * 请在使用前保证fileName和projectName不为null
     */
    @Override
    public TaskSQLParserDO publish(String projectName, String fileName) {
        logger.info("task publish, projectName: " + projectName + ", fileName: " + fileName);
        DolPublishDO publishDO = publishDolFile(projectName, fileName);
        logger.info("publish result :" + publishDO.isSuccess);
        if (!publishDO.isSuccess)
            return getErrorParserDO(publishDO.getMessage());
        String dolPath = getDolPath(fileName);
        return parse(dolPath);
    }


    @Override
    public int rollback(String gitPath, String filePath) {
        return 0;
    }

    /**
     * SQL解析
     * 请在使用前保证dolPath不为null
     */
    private TaskSQLParserDO parse(String dolPath) {
        Parser parser = new Parser();
        Map<Integer, AnalyzeResult> resultMap;
        try {
            resultMap = parser.parse(dolPath);
        } catch (Exception e) {
            logger.error("parse dol error", e);
            return getErrorParserDO("parse dol error");
        }
        if (resultMap == null)
            return getErrorParserDO("解析DOL结果为空");
        return getTaskSQLParserDOFromAnalyseResult(resultMap);
    }

    /**
     * 将解析结果封装成TaskSQLParserDO对象
     * 请在使用前保证resultMap不为null
     */
    private TaskSQLParserDO getTaskSQLParserDOFromAnalyseResult(Map<Integer, AnalyzeResult> resultMap) {
        TaskSQLParserDO taskSQLParserDO = new TaskSQLParserDO();
        taskSQLParserDO.setSuccess(true);
        List<String> childTableNames = getChildTableNames(resultMap);
        List<String> parentTableNames = getParentTableNames(resultMap);
        List<TaskRelaDO> taskRelaDOs = getTaskRelaDOsByTableNames(parentTableNames);
        taskSQLParserDO.setChildTableNames(childTableNames);
        taskSQLParserDO.setParentTableNames(parentTableNames);
        taskSQLParserDO.setParentTaskRelaDOs(taskRelaDOs);
        return taskSQLParserDO;
    }

    /**
     * DOL任务发布，将项目GIT PULL到本地，然后上传到HDFS
     * 请在使用前保证projectName和fileName不为null
     */
    private DolPublishDO publishDolFile(String projectName, String fileName) {
        logger.info("publish project :" + projectName + " file :" + fileName + " starts");
        try {
            if (!gitPull(projectName))
                return new DolPublishDO(false, "git pull失败");
            if (!hdfsUpload(fileName))
                return new DolPublishDO(false, "hdfs upload失败");

            PublishFileDO file = new PublishFileDO(projectName, fileName);
            logger.info("file publish_id :" + file.getPublishId());
            serverNode.pushJobQueue(file);
            Thread.sleep(5000);
            List<PublishFileDO> publishFiles = publishLogDAO.getPublishListByID(file.getPublishId());
            String[] hosts = LionUtil.getProperty("galaxy-halley.worker_hosts").split(";");
            if (!validateHost(publishFiles, hosts))
                return new DolPublishDO(false, "validate host失败");
            return new DolPublishDO(true, "");
        } catch (Exception e) {
            logger.error("publish file error", e);
            return new DolPublishDO(false, "publish file失败");
        } finally {
            logger.info("publish project :" + projectName + " file :" + fileName + " ends");
        }
    }

    public boolean validateHost(List<PublishFileDO> publishFiles, String[] hosts) {
        logger.info("publish files size: " + publishFiles.size());
        logger.info("hosts: " + hosts.length);
        boolean flag = false;
        for (String host : hosts) {
            flag = false;
            for (PublishFileDO publishFileDO : publishFiles) {
                if (!publishFileDO.isFlag()) {
                    return false;
                }
                if (host.equals(publishFileDO.getHost())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 从GIT上拉项目到本地
     * 请在使用前保证projectName不为null
     */
    private boolean gitPull(String projectName) {
        String gitPath = JGitUtils.getGitPath(projectName);
        if (gitPath == null) {
            logger.error(projectName + "is not correct");
            return false;
        }
        if (!JGitUtils.pull(gitPath, projectName)) {
            logger.error("git pull dolfile:" + gitPath + " failure");
            return false;
        }
        return true;
    }

    /**
     * 将文件上传到HDFS上
     * 请在使用前保证fileName不为null
     */
    private boolean hdfsUpload(String fileName) {
        String path = HalleyStrUtils.concatPathStr(Const.MASTER_GIT_DIR, fileName);
        String hdfsPath = HalleyStrUtils.concatPathStr(Const.HDFS_DIR, fileName);
        if (!HdfsUtils.upload(path, hdfsPath)) {
            logger.error("upload to hdfs dolfile:" + hdfsPath + " failure");
            return false;
        }
        return true;
    }


    /**
     * 从SQL解析结果中获得目标表名
     * 请在使用前保证resultMap不为null
     */
    private List<String> getChildTableNames(Map<Integer, AnalyzeResult> resultMap) {
        List<String> tableNames = new ArrayList<String>();
        for (Map.Entry<Integer, AnalyzeResult> entry : resultMap.entrySet()) {
            Integer lineNumber = entry.getKey();
            for (String childTableName : entry.getValue().getChildTabs()) {
                childTableName = removeSchemaName(childTableName);
                if (!isTemChildTable(childTableName, lineNumber, resultMap) && !tableNames.contains(childTableName)) {
                    tableNames.add(childTableName);
                }
            }
        }
        return tableNames;
    }

    /**
     * 从SQL解析结果中获得源表名
     * 请在使用前保证resultMap不为null
     */
    private List<String> getParentTableNames(Map<Integer, AnalyzeResult> resultMap) {
        List<String> tableNames = new ArrayList<String>();
        for (Map.Entry<Integer, AnalyzeResult> entry : resultMap.entrySet()) {
            Integer lineNumber = entry.getKey();
            for (String parentTableName : entry.getValue().getParentTabs()) {
                parentTableName = removeSchemaName(parentTableName);
                if (!isTemParentTable(parentTableName, lineNumber, resultMap) && !tableNames.contains(parentTableName)) {
                    tableNames.add(parentTableName);
                }
            }
        }
        return tableNames;
    }

    /*
     * 判断SQL解析结果中某个目标表是否为中间表
     * 请在使用前保证参数均不为null
     */
    private boolean isTemChildTable(String childTableName, Integer
            childLineNumber, Map<Integer, AnalyzeResult> resultMap) {
        for (Map.Entry<Integer, AnalyzeResult> entry : resultMap.entrySet()) {
            Integer lineNumber = entry.getKey();
            //在之后的SQL语句中该目标表又作为源表出现
            if (lineNumber > childLineNumber) {
                for (String parentTableName : entry.getValue().getParentTabs()) {
                    parentTableName = removeSchemaName(parentTableName);
                    if (parentTableName.equalsIgnoreCase(childTableName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断SQL解析结果中某个源表是否为中间表
     * 请在使用前保证参数均不为null
     */
    private boolean isTemParentTable(String parentTableName, Integer
            parentLineNumber, Map<Integer, AnalyzeResult> resultMap) {
        for (Map.Entry<Integer, AnalyzeResult> entry : resultMap.entrySet()) {
            Integer lineNumber = entry.getKey();
            //在之前的SQL语句中该源表是目标表
            if (lineNumber < parentLineNumber) {
                for (String childTableName : entry.getValue().getChildTabs()) {
                    childTableName = removeSchemaName(childTableName);
                    if (childTableName.equalsIgnoreCase(parentTableName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 从SQL解析结果中获得依赖任务
     * 请在使用前保证tableNames不为null
     */
    private List<TaskRelaDO> getTaskRelaDOsByTableNames(List<String> tableNames) {
        tableNames = removeSchemaNames(tableNames);
        List<TaskRelaDO> taskRelaDOs = new ArrayList<TaskRelaDO>();
        if (tableNames == null || tableNames.isEmpty())
            return new ArrayList<TaskRelaDO>();
        List<Integer> parentTaskIds = taskDAO.getParentTaskIdList(tableNames);
        if (parentTaskIds == null || parentTaskIds.isEmpty())
            return new ArrayList<TaskRelaDO>();
        List<TaskDO> taskDOs = taskDAO.getValidTasksByTaskIds(parentTaskIds);
        for (TaskDO taskDO : taskDOs) {
            TaskRelaDO taskRelaDO = new TaskRelaDO();
            taskRelaDO.setTaskPreId(taskDO.getTaskId());
            taskRelaDO.setTaskName(taskDO.getTaskName());
            taskRelaDO.setOwner(taskDO.getOwner());
            taskRelaDO.setCycleGap(taskDO.getCycle() + "0");
            taskRelaDOs.add(taskRelaDO);
        }
        return taskRelaDOs;
    }

    /**
     * 根据fileName获得本地DOL路径
     * 请在使用前保证fileName不为null
     */
    private String getDolPath(String fileName) {
        return HalleyStrUtils.concatPathStr(Const.MASTER_GIT_DIR, fileName);
    }

    /**
     * 去除schema获取真正的tableName。如果有.则最后一个.前为schema name，.后为table name。最后要去除空格
     */
    private List<String> removeSchemaNames(List<String> tableNames) {
        List<String> names = new ArrayList<String>();
        for (String tableName : tableNames) {
            int index = tableName.indexOf(".");
            if (index != -1)
                names.add(tableName.substring(index + 1));
            else
                names.add(tableName);
        }
        return names;
    }

    /**
     * 去除schema获取真正的tableName。如果有.则最后一个.前为schema name，.后为table name。最后要去除空格
     */
    private String removeSchemaName(String tableName) {
        String name = tableName;
        int index = tableName.lastIndexOf(".");
        if (index != -1)
            name = tableName.substring(index + 1);
        return name.trim();
    }

    private TaskSQLParserDO getErrorParserDO(String message) {
        TaskSQLParserDO taskSQLParserDO = new TaskSQLParserDO();
        taskSQLParserDO.setSuccess(false);
        taskSQLParserDO.setMessage(message);
        return taskSQLParserDO;
    }

    class DolPublishDO {
        private boolean isSuccess;
        private String message;

        public DolPublishDO(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
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
}
