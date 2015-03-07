package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.HalleyConst;
import com.dianping.data.warehouse.core.common.ValidatorUtils;
import com.dianping.data.warehouse.core.dao.InstanceDAO;
import com.dianping.data.warehouse.core.dao.TaskDAO;
import com.dianping.data.warehouse.core.dao.TaskRelaDAO;
import com.dianping.data.warehouse.core.util.DateFormatUtils;
import com.dianping.data.warehouse.halley.domain.*;
import com.dianping.data.warehouse.halley.service.TaskService;
import com.dianping.data.warehouse.core.util.HalleyUtil;

import com.dianping.pigeon.remoting.provider.config.annotation.Service;
import org.hsqldb.lib.StringUtil;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.*;

/**
 * Created by hongdi.tang on 14-2-12.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Resource
    private TaskDAO taskDAO;

    @Resource
    private TaskRelaDAO taskRelaDAO;

    @Resource
    private InstanceDAO instDAO;

    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public TaskRelaDAO getTaskRelaDAO() {
        return taskRelaDAO;
    }

    public InstanceDAO getInstDAO() {
        return instDAO;
    }

    public void setInstDAO(InstanceDAO instDAO) {
        this.instDAO = instDAO;
    }

    public void setTaskRelaDAO(TaskRelaDAO taskRelaDAO) {
        this.taskRelaDAO = taskRelaDAO;
    }

    /**
     * 根据taskId获得task
     */
    @Override
    public TaskDO getTaskByTaskId(Integer id) {
        logger.info("get task by taskId: " + id);
        TaskDO taskDO = taskDAO.getTaskByTaskId(id);
        if (taskDO == null)
            return null;
        List<TaskRelaDO> list = taskRelaDAO.getTaskRelaByID(id);
        taskDO.setRelaDOList(list);
        return taskDO;
    }

    /**
     * 根据条件查询task
     */
    @Override
    public List<TaskDO> queryTasks(TaskQueryDO taskQueryDO) {
        logger.info("query tasks, group: " + taskQueryDO.getGroup() + ", cycle: " + taskQueryDO.getCycle() +
                ", developer: " + taskQueryDO.getDeveloper() + ", taskIds: " + taskQueryDO.getTaskIds() +
                ", taskName: " + taskQueryDO.getTaskName());
        return taskDAO.queryTasksByTaskQueryDO(taskQueryDO);
    }

    /**
     * 预跑任务
     */
    @Override
    public boolean preRunTask(Integer taskId, String begin, String end, String committer) {
        logger.info("prerun task, taskId: " + taskId + ", begin: " + begin + ", end: " + end +
                ", committer: " + committer);
        Date startDate = null;
        Date endDate = null;
        if (taskId == null || null == begin || null == end) {
            logger.error("parameter(taskList,begin,end) is not null");
            return false;
        }
        try {
            startDate = HalleyUtil.string2Date(begin);
            endDate = HalleyUtil.string2Date(end);
        } catch (ParseException e) {
            logger.error("date format is illegal");
            return false;
        }
        if (startDate.after(endDate)) {
            logger.error("startDate must less than endDate!");
            return false;
        }
        Date triggerDate = new Date();
        if (startDate.after(triggerDate)) {
            logger.error("startDate must less than today!");
            return false;
        }
        endDate = endDate.after(triggerDate) ? triggerDate : endDate;
        Date initDate = startDate;
        TaskDO taskDO = this.getTaskByTaskId(taskId);
        String freq = taskDO.getFreq();
        CronExpression ce = null;
        try {
            ce = new CronExpression(freq);
        } catch (ParseException e) {
            logger.error("freq is illegal for cronExpression");
            return false;
        }
        while (true) {
            initDate = ce.getNextValidTimeAfter(initDate);
            if (initDate.after(endDate)) {
                break;
            }
            InstanceDO instanceDO = this.getInitInstance(taskDO, initDate, triggerDate);
            instanceDO.setTask_committer(committer);
            instDAO.insertInstance(instanceDO);
        }
        return true;
    }

    @Override
    public boolean preRunJobAscend(Integer taskId, String begin, String end, String committer) {
        logger.info("prerun task ascend, taskId: " + taskId + ", begin: " + begin + ", end: " + end +
                ", committer: " + committer);
        Date startDate = null;
        Date endDate = null;

        if (null == taskId || null == begin || null == end) {
            logger.error("parameter(taskId,begin,end) is not null");
            return false;
        }
        try {
            startDate = HalleyUtil.string2Date(begin);
            endDate = HalleyUtil.string2Date(end);
        } catch (ParseException e) {
            logger.error("date format is illegal");
            return false;
        }
        if (startDate.after(endDate)) {
            logger.error("startDate must less than endDate!");
            return false;
        }
        Date triggerDate = new Date();
        if (startDate.after(triggerDate)) {
            logger.error("startDate must less than today!");
            return false;
        }
        endDate = endDate.after(triggerDate) ? triggerDate : endDate;
        Date initDate = startDate;
        TaskDO taskDO = this.getTaskByTaskId(taskId);
        String freq = taskDO.getFreq();
        CronExpression ce = null;

        try {
            ce = new CronExpression(freq);
        } catch (ParseException e) {
            logger.error("freq is illegal for cronExpression");
            return false;
        }
        InstanceDO instanceDO = null;
        InstanceRelaDO relaDO = new InstanceRelaDO();
        int i = 0;
        while (true) {
            initDate = ce.getNextValidTimeAfter(initDate);
            if (initDate.after(endDate)) {
                break;
            }
            if (i > 0) {
                relaDO.setPreId(instanceDO.getTaskId());
                relaDO.setPreInstanceId(instanceDO.getInstanceId());
            }
            instanceDO = this.getInitInstance(taskDO, initDate, triggerDate);
            if (i > 0) {
                instanceDO.setIfPre(1);
            }
            if (i > 0) {
                relaDO.setInstanceId(instanceDO.getInstanceId());
                relaDO.setTaskId(instanceDO.getTaskId());
                relaDO.setTimestamp(DateFormatUtils.getFormatter().format(new Date()));
                taskDAO.insertInstanceRela(relaDO);
            }
            instanceDO.setTask_committer(committer);
            instDAO.insertInstance(instanceDO);
            i++;
        }
        return true;
    }

    /**
     * 删除taskId对应的任务
     */
    @Override
    public boolean deleteTaskByTaskId(Integer id, String updateTime, String updateUser) {
        logger.info("delete task by taskId, taskId: " + id + ", updateTime: " + updateTime + ", updateUser:"
                + updateUser);
        taskDAO.deleteTask(id, updateTime, updateUser);
        taskRelaDAO.deleteTaskRela(id);
        return true;
    }

    /**
     * 新增任务
     */
    @Override
    public boolean insertTask(TaskDO task) {
        logger.info("insert task, taskId: " + task.getTaskId());
        ValidatorUtils.validateModel(task);
        taskDAO.insertTask(task);
        if (task.getRelaDOList() != null && !task.getRelaDOList().isEmpty()) {
            taskRelaDAO.insertTaskRela(task.getRelaDOList());
            task.setIfPre(HalleyConst.TASK_EXISTS_PRE);
        } else {
            task.setIfPre(HalleyConst.TASK_NON_EXISTS_PRE);
        }
        return true;
    }

    /**
     * 更新任务
     */
    @Override
    public boolean updateTask(TaskDO task) {
        logger.info("update task, taskId: " + task.getTaskId());
        taskDAO.updateTask(task);
        taskRelaDAO.deleteTaskRela(task.getTaskId());
        if (task.getRelaDOList() != null && !task.getRelaDOList().isEmpty()) {
            taskRelaDAO.insertTaskRela(task.getRelaDOList());
            task.setIfPre(HalleyConst.TASK_EXISTS_PRE);
        } else {
            task.setIfPre(HalleyConst.TASK_NON_EXISTS_PRE);
        }
        taskDAO.updateTask(task);
        return true;
    }

    /**
     * 检测是否存在环依赖
     */
    @Override
    public boolean hasCycleDependence(TaskDO task) {
        logger.info("check has cycle dependence, taskId: " + task.getTaskId() + ", taskName: " + task.getTaskName());
        List<TaskRelaDO> initTaskRelaDOs = task.getRelaDOList();
        if (initTaskRelaDOs == null || initTaskRelaDOs.isEmpty())
            return false;
        return checkCycle(task);
    }

    /**
     * 将taskId对用的任务生效
     */
    @Override
    public boolean validTaskByTaskId(Integer id, String updateTime, String updateUser) {
        logger.info("valid task by taskId, taskId: " + id + ", updateTime: " + updateTime + ", updateUser" + updateUser);
        taskDAO.updateTaskStatus(id, 1, updateTime, updateUser);
        return true;
    }

    /**
     * 将taskId对用的任务失效
     */
    @Override
    public boolean invalidTaskByTaskId(Integer id, String updateTime, String updateUser) {
        logger.info("invalid task by taskId, taskId: " + id + ", updateTime: " + updateTime + ", updateUser" + updateUser);
        taskDAO.updateTaskStatus(id, 0, updateTime, updateUser);
        return true;
    }

    /**
     * 根据task的信息生成taskId，要保证唯一性
     */
    @Override
    public synchronized Integer generateTaskID(TaskDO taskEntity) {
        int taskGroupId = taskEntity.getTaskGroupId();
        String databaseSrc = taskEntity.getDatabaseSrc();
        int iDatabaseSrc = 0;
        if (databaseSrc.equalsIgnoreCase(HalleyConst.DATABASE_TYPE_HIVE)) {
            iDatabaseSrc = 1;
        } else if (databaseSrc.equalsIgnoreCase(HalleyConst.DATABASE_TYPE_GP57)) {
            iDatabaseSrc = 2;
        } else if (databaseSrc.equalsIgnoreCase(HalleyConst.DATABASE_TYPE_GP59)) {
            iDatabaseSrc = 3;
        }
        List<Integer> taskIds = new LinkedList<Integer>();

        for (Integer taskId : taskDAO.getTaskIDList()) {
            if (taskGroupId == 1 && taskId < 100000) {
                taskIds.add(taskId);
            } else if (taskId / 100000 == taskGroupId && taskId % 10 == iDatabaseSrc) {
                taskIds.add(taskId / 10);
            }
        }
        int taskID = -1;
        for (int i = taskGroupId * 10000 + 1; i < (taskGroupId + 1) * 10000; ++i) {
            if (!taskIds.contains(i)) {
                taskID = i;
                break;
            }
        }
        if (taskGroupId != 1) {
            taskID = taskID * 10 + iDatabaseSrc;
        }
        logger.info("generate taskId, taskId: " + taskID + ", task name: " + taskEntity.getTaskName());
        return taskID;
    }

    /**
     * 检测指定task name是否存在，不考虑废弃任务
     */
    public boolean taskNameExists(String taskName) {
        logger.info("check task exist, taskName: " + taskName);
        return taskDAO.getTaskIdByTaskName(taskName) != null;
    }

    public List<Integer> getParentTaskIdList(List<String> tableNames) {
        logger.info("get parent taskIds, tabelNames: " + tableNames);
        return taskDAO.getParentTaskIdList(tableNames);
    }

    @Override
    public List<TaskDO> getValidTasksByTaskIds(List<Integer> taskIds) {
        logger.info("get valid tasks by taskIds, taskIds: " + taskIds);
        return taskDAO.getValidTasksByTaskIds(taskIds);
    }

    public Map<String, TaskDO> getTaskByTable(List<String> tables) {
        logger.info("get task by table, tables: " + tables);
        Map<String, TaskDO> result = new HashMap<String, TaskDO>();
        List<Integer> ids = taskDAO.getParentTaskIdList(tables);
        List<TaskDO> tasks = taskDAO.getValidTasksByTaskIds(ids);
        return null;
    }

    @Override
    public List<Map<String, Integer>> getTaskIdsByTable(List<String> tables) {
        logger.info("get taskIds by table, tables: " + tables);
        return taskDAO.getParentTaskIdsByTables(tables);
    }

    /**
     * 根据taskId获得其所有后续任务
     * 返回后续任务和自身所组成的list
     */
    @Override
    public List<TaskDO> getPostTasksByTaskId(Integer taskId) {
        logger.info("get post tasks by taskId, taskId: " + taskId);
        //获得task
        TaskDO taskDO = taskDAO.getTaskByTaskId(taskId);
        if (taskDO == null)
            return new ArrayList<TaskDO>();
        //初始的task
        List<TaskDO> initTaskDOs = new ArrayList<TaskDO>();
        initTaskDOs.add(taskDO);
        //获得taskId到post tasks的映射
        Map<Integer, List<TaskDO>> taskIdToTasksMap = getTaskIdToPostTasksMap();
        //获得所有需要展示的task
        List<TaskDO> allTasksNeedToDisplay = getAllRelatedTasks(initTaskDOs, taskIdToTasksMap);
        return allTasksNeedToDisplay;
    }

    /**
     * 获取级联重跑所有需要重跑的instance
     */
    @Override
    public List<TaskRelaDO> getTasksForCascadePreRun(Integer taskId) {
        logger.info("get tasks for cascade prerun, taskId: " + taskId);
        //获得task
        TaskDO taskDO = taskDAO.getTaskByTaskId(taskId);
        if (taskDO == null)
            return new ArrayList<TaskRelaDO>();
        //获得taskId到post tasks的映射
        Map<Integer, List<TaskDO>> taskIdToPostTasksMap = getTaskIdToPostTasksMap();
        //获得所有需要展示的task
        List<TaskRelaDO> taskRelaDOs = getAllRelatedTaskRelations(taskDO, taskIdToPostTasksMap);
        return taskRelaDOs;
    }

    /**
     * 级联预跑
     */
    @Override
    public boolean cascadePreRun(String startDate, String endDate, List<String> taskIds, String committer) {
        logger.info("cascade prerun: startDate " + startDate + ", endDate " + endDate + ", " + taskIds);
        //插入实例依赖
        insertInstanceRelationsForCascadePreRun(startDate, endDate, taskIds);
        //插入实例
        insertInstanceRelationsForCascadePreRun(startDate, endDate, taskIds, committer);
        return true;
    }


    private void insertInstanceRelationsForCascadePreRun(String startDate, String endDate, List<String> taskIds,
                                                         String committer) {
        for (int i = 0; i < taskIds.size(); i++) {
            preRunTask(Integer.parseInt(taskIds.get(i)), startDate, endDate, committer);
        }
    }


    private void insertInstanceRelationsForCascadePreRun(String startDate, String endDate, List<String> taskIds) {
        //获得taskId到pre taskIds的映射
        Map<Integer, List<Integer>> taskIdToPreTaskIdsMap = getTaskIdToPreTaskIdsMap();
        for (int i = 0; i < taskIds.size(); i++) {
            Integer id = Integer.parseInt(taskIds.get(i));
            List<Integer> preIds = taskIdToPreTaskIdsMap.get(id);
            if (preIds != null) {
                for (int j = 0; j < preIds.size(); j++) {
                    Integer preId = preIds.get(j);
                    if (taskIds.contains(preId.toString())) {
                        createInstanceRelation(startDate, endDate, preId, id);
                    }
                }
            }
        }
    }

    private void createInstanceRelation(String start, String end, Integer taskPreId, Integer taskId) {
        Date triggerDate = new Date();
        Date startDate, endDate;
        try {
            startDate = HalleyUtil.string2Date(start);
            endDate = HalleyUtil.string2Date(end);
        } catch (ParseException e) {
            logger.error("date format is illegal");
            return;
        }
        endDate = endDate.after(triggerDate) ? triggerDate : endDate;
        Date initDate = startDate;
        CronExpression ce = null;
        try {
            ce = new CronExpression("0 5 0 * * ?");
        } catch (ParseException e) {
            logger.error("freq is illegal for cronExpression");
            return;
        }
        while (true) {
            initDate = ce.getNextValidTimeAfter(initDate);
            if (initDate.after(endDate)) {
                break;
            }
            String instanceId = getInstanceIdByTaskId(taskId, initDate, triggerDate);
            String instancePreId = getInstanceIdByTaskId(taskPreId, initDate, triggerDate);
            InstanceRelaDO instanceRelaDO = new InstanceRelaDO(instanceId, taskId, instancePreId, taskPreId,
                    DateFormatUtils.getFormatter().format(new Date()));
            taskDAO.insertInstanceRela(instanceRelaDO);
        }
    }

    /**
     * 获得taskId到post tasks的映射
     * 只考虑关系为D0的情况
     */
    private Map<Integer, List<TaskDO>> getTaskIdToPostTasksMap() {
        //获得所有的task
        List<TaskDO> taskDOs = taskDAO.getDayTasks();
        //获得所有的taskrela
        List<TaskRelaDO> relaDOs = taskRelaDAO.getAllTaskRelations();
        //获得taskId到task的映射
        Map<Integer, TaskDO> taskIdToTaskMap = getTaskIdToTaskMap(taskDOs);
        //taskId到子taskId的映射
        Map<Integer, List<TaskDO>> taskIdToPostTasksMap = new HashMap<Integer, List<TaskDO>>();
        //设置初始值，所有的taskId对应的post tasks全为空
        for (TaskDO taskDO : taskDOs) {
            taskIdToPostTasksMap.put(taskDO.getTaskId(), new ArrayList<TaskDO>());
        }
        //从顶点遍历
        for (TaskRelaDO relation : relaDOs) {
            if (relation.getCycleGap().equals("D0")) {
                Integer preId = relation.getTaskPreId();
                Integer id = relation.getTaskId();
                if (taskIdToTaskMap.containsKey(preId) && taskIdToTaskMap.containsKey(id)) {
                    taskIdToPostTasksMap.get(preId).add(taskIdToTaskMap.get(id));
                }
            }
        }
        return taskIdToPostTasksMap;
    }

    /**
     * 获得taskId到pre tasks的映射
     * 只考虑关系为D0的情况
     */
    private Map<Integer, List<TaskDO>> getTaskIdToPreTasksMap() {
        //获得所有的task
        List<TaskDO> taskDOs = taskDAO.getDayTasks();
        //获得所有的taskrela
        List<TaskRelaDO> relaDOs = taskRelaDAO.getAllTaskRelations();
        //获得taskId到task的映射
        Map<Integer, TaskDO> taskIdToTaskMap = getTaskIdToTaskMap(taskDOs);
        //taskId到子taskId的映射
        Map<Integer, List<TaskDO>> taskIdToPreTasksMap = new HashMap<Integer, List<TaskDO>>();
        //设置初始值，所有的taskId对应的pre tasks全为空
        for (TaskDO taskDO : taskDOs) {
            taskIdToPreTasksMap.put(taskDO.getTaskId(), new ArrayList<TaskDO>());
        }
        for (TaskRelaDO relation : relaDOs) {
            if (relation.getCycleGap().equals("D0")) {
                Integer preId = relation.getTaskPreId();
                Integer id = relation.getTaskId();
                if (taskIdToTaskMap.containsKey(preId) && taskIdToTaskMap.containsKey(id)) {
                    taskIdToPreTasksMap.get(id).add(taskIdToTaskMap.get(preId));
                }
            }
        }
        return taskIdToPreTasksMap;
    }

    /**
     * 获得taskId到pre taskIds的映射
     * 只考虑关系为D0的情况
     */
    private Map<Integer, List<Integer>> getTaskIdToPreTaskIdsMap() {
        //获得所有的taskrela
        List<TaskRelaDO> relaDOs = taskRelaDAO.getAllTaskRelations();
        //taskId到子taskId的映射
        Map<Integer, List<Integer>> taskIdToPreTaskIdsMap = new HashMap<Integer, List<Integer>>();
        for (TaskRelaDO relation : relaDOs) {
            if (relation.getCycleGap().equals("D0")) {
                Integer preId = relation.getTaskPreId();
                Integer id = relation.getTaskId();
                if (taskIdToPreTaskIdsMap.containsKey(id)) {
                    taskIdToPreTaskIdsMap.get(id).add(preId);
                } else {
                    List<Integer> ids = new ArrayList<Integer>();
                    ids.add(preId);
                    taskIdToPreTaskIdsMap.put(id, ids);
                }
            }
        }
        return taskIdToPreTaskIdsMap;
    }

    /**
     * 返回taskId到task本身的map
     */
    private Map<Integer, TaskDO> getTaskIdToTaskMap(List<TaskDO> taskDOs) {
        Map<Integer, TaskDO> taskIdToTaskMap = new HashMap<Integer, TaskDO>();
        for (TaskDO taskDO : taskDOs)
            taskIdToTaskMap.put(taskDO.getTaskId(), taskDO);
        return taskIdToTaskMap;
    }

    /**
     * 获得所有需要展示的tasks
     */
    private List<TaskDO> getAllRelatedTasks(List<TaskDO> taskDOs, Map<Integer, List<TaskDO>> taskRelations) {
        Set<Integer> extraTaskIdSet = new HashSet<Integer>();
        Map<Integer, TaskDO> taskDONeedToDisplayMap = new HashMap<Integer, TaskDO>();
        for (TaskDO taskDO : taskDOs) {
            extraTaskIdSet.add(taskDO.getTaskId());
            taskDONeedToDisplayMap.put(taskDO.getTaskId(), taskDO);
        }
        //循环获得所有的子节点的taskId并加入taskIdSet
        while (hasAndSetChildTask(taskDONeedToDisplayMap, taskRelations)) ;
        List<TaskDO> result = new ArrayList<TaskDO>();
        for (Map.Entry<Integer, TaskDO> entry : taskDONeedToDisplayMap.entrySet())
            result.add(entry.getValue());
        return result;
    }

    /**
     * 获得所有需要展示的taskrelations
     */
    private List<TaskRelaDO> getAllRelatedTaskRelations(TaskDO initTask, Map<Integer, List<TaskDO>> taskRelations) {
        List<TaskRelaDO> taskRelaDOs = new ArrayList<TaskRelaDO>();
        taskRelaDOs.add(new TaskRelaDO(0, initTask.getTaskId(), initTask.getTaskName()));
        List<Integer> taskIds = new ArrayList<Integer>();
        taskIds.add(taskRelaDOs.get(0).getTaskId());
        while (!taskIds.isEmpty()) {
            Integer preId = taskIds.remove(0);
            List<TaskDO> taskDOs = taskRelations.get(preId);
            for (TaskDO taskDO : taskDOs) {
                taskIds.add(taskDO.getTaskId());
                taskRelaDOs.add(new TaskRelaDO(preId, taskDO.getTaskId(), taskDO.getTaskName()));
            }
        }
        return taskRelaDOs;
    }

    /**
     * 遍历taskRelations的元素，查找是task list的中某个task的子节点又不在tasks集合中的节点，
     * 将其插入到tasks中，如果存在这样的元素返回true，否则否会false
     */
    private boolean hasAndSetChildTask(Map<Integer, TaskDO> taskDOMap, Map<Integer, List<TaskDO>> taskRelations) {
        boolean flag = false;
        for (Map.Entry<Integer, List<TaskDO>> entry : taskRelations.entrySet()) {
            Integer id = entry.getKey();
            List<TaskDO> childTasks = entry.getValue();
            if (taskDOMap.containsKey(id)) {
                for (TaskDO taskDO : childTasks) {
                    if (!taskDOMap.containsKey(taskDO.getTaskId())) {
                        taskDOMap.put(taskDO.getTaskId(), taskDO);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    private InstanceDO getInitInstance(TaskDO entity, Date initDate, Date triggerDate) {
        InstanceDO result = new InstanceDO();
        String instanceId = "pre_" + HalleyUtil.generateInstanceId(String.valueOf(entity.getTaskId()), String.valueOf(entity.getCycle()), initDate) + "_" + HalleyUtil.Date2String(triggerDate);
        result.setInstanceId(instanceId);
        result.setTaskId(entity.getTaskId());
        result.setTaskGroupId(entity.getTaskGroupId());
        result.setTaskName(entity.getTaskName());
        result.setTaskObj(HalleyUtil.dirReplace(entity.getTaskObj()));
        result.setPara1(StringUtil.isEmpty(entity.getPara1()) ? null : HalleyUtil.CaldtReplace(entity.getPara1(), entity.getOffsetType(), entity.getOffset(), initDate).replace("${task_id}", String.valueOf(entity.getTaskId())));
        result.setPara2(StringUtil.isEmpty(entity.getPara2()) ? null : HalleyUtil.CaldtReplace(entity.getPara2(), entity.getOffsetType(), entity.getOffset(), initDate).replace("${task_id}", String.valueOf(entity.getTaskId())));
        result.setPara3(StringUtil.isEmpty(entity.getPara3()) ? null : HalleyUtil.CaldtReplace(entity.getPara3(), entity.getOffsetType(), entity.getOffset(), initDate).replace("${task_id}", String.valueOf(entity.getTaskId())));
        String logPath = HalleyUtil.dirReplace(entity.getLogHome()) + File.separator + entity.getLogFile() + "." + instanceId + "." + HalleyUtil.getDay8();
        result.setLogPath(logPath);
        result.setCycle(entity.getCycle());
        result.setTimeId(HalleyUtil.getDay10(initDate));
        result.setStatus(0);
        result.setPrioLvl(5);
        result.setRunNum(0);
        result.setType(entity.getType());
        result.setTableName(entity.getTableName());
        String baseCaldt = HalleyUtil.getLastDay10(initDate);
        String caldt = null;
        try {
            caldt = HalleyUtil.getCalDt(baseCaldt, entity.getOffsetType(), entity.getOffset());
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            caldt = null;
        }

        result.setCalDt(caldt);
        result.setDatabaseSrc(entity.getDatabaseSrc());
        result.setIfPre(0);
        result.setIfWait(entity.getIfWait());
        result.setIfRecall(0);
        result.setStsDesc("INIT");
        result.setRecallNum(0);
        result.setOwner(entity.getOwner());
        Calendar c = Calendar.getInstance();
        c.setTime(triggerDate);
        result.setTriggerTime(c.getTimeInMillis());
        result.setRecallCode(entity.getRecallCode());
        result.setSuccessCode(entity.getSuccessCode());
        result.setWaitCode(entity.getWaitCode());
        result.setJobCode(-1);
        result.setFreq(entity.getFreq());
        result.setTimeout(entity.getTimeout());
        result.setRecallInterval(entity.getRecallInterval());
        result.setRecallLimit(entity.getRecallLimit());
        result.setRunningPrio(-1);
        return result;
    }

    /**
     * 传入参数是否是taskId
     */
    private boolean isTaskId(String taskNameOrId) {
        if (taskNameOrId == null || taskNameOrId.equals(""))
            return false;
        char array[] = taskNameOrId.toCharArray();//把字符串转换为字符数组
        for (int i = 0; i < array.length; i++) {
            if (!Character.isDigit(array[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据传入的taskDOs获得其taskId的集合
     */
    private Set<Integer> getTaskIdSetByTasks(List<TaskDO> taskDOs) {
        Set<Integer> taskIdSet = new HashSet<Integer>();
        for (int i = 0; i < taskDOs.size(); i++) {
            taskIdSet.add(taskDOs.get(i).getTaskId());
        }
        return taskIdSet;
    }

    private String getInstanceIdByTaskId(Integer taskId, Date initDate, Date triggerDate) {
        String instanceId = "pre_" + HalleyUtil.generateInstanceId(String.valueOf(taskId), "D", initDate) + "_" + HalleyUtil.Date2String(triggerDate);
        return instanceId;
    }

    /**
     * 检测taskDO是否在taskIdToTaskPreIds中产生依赖环
     */
    private boolean checkCycle(TaskDO task) {
        Map<Integer, List<Integer>> taskIdToTaskPreIds = getTaskIdToTaskPreIds(task);
        List<Integer> initTaskIds = new ArrayList<Integer>();
        initTaskIds.add(task.getTaskId());
        while (!initTaskIds.isEmpty()) {
            Integer taskId = initTaskIds.remove(0);
            List<Integer> taskPreIds = taskIdToTaskPreIds.get(taskId);
            if (taskPreIds != null && !taskPreIds.isEmpty()) {
                if (taskPreIds.contains(task.getTaskId()))
                    return true;
                initTaskIds.addAll(taskPreIds);
            }
        }
        return false;
    }

    /**
     * 获得taskId到taskPreIds的Map
     */
    private Map<Integer, List<Integer>> getTaskIdToTaskPreIds(TaskDO task) {
        List<TaskRelaDO> allTaskRelaDOs = getNewAllTaskRelaDOs(task);
        Map<Integer, List<Integer>> taskIdToTaskPreIds = new HashMap<Integer, List<Integer>>();
        for (TaskRelaDO taskRelaDO : allTaskRelaDOs) {
            Integer taskId = taskRelaDO.getTaskId();
            Integer taskPreId = taskRelaDO.getTaskPreId();
            String cycleGap = taskRelaDO.getCycleGap();
            if (cycleGap.endsWith("0")) { //如果末尾为1，则不可能再关联到今天的实例，所以只考虑末尾为0的情况
                if (taskIdToTaskPreIds.containsKey(taskId)) {
                    taskIdToTaskPreIds.get(taskId).add(taskPreId);
                } else {
                    List<Integer> preIds = new ArrayList<Integer>();
                    preIds.add(taskPreId);
                    taskIdToTaskPreIds.put(taskId, preIds);
                }
            }
        }
        return taskIdToTaskPreIds;
    }

    /**
     * 根据新的task的依赖更新源task的依赖，并返回所有的taskRelaDOs
     */
    private List<TaskRelaDO> getNewAllTaskRelaDOs(TaskDO task) {
        List<TaskRelaDO> taskRelaDOs = taskRelaDAO.getAllTaskRelations();
        List<TaskRelaDO> allTaskRelaDOs = new ArrayList<TaskRelaDO>();
        for (TaskRelaDO taskRelaDO : taskRelaDOs) {
            if (taskRelaDO.getTaskId() != task.getTaskId())
                allTaskRelaDOs.add(taskRelaDO);
        }
        allTaskRelaDOs.addAll(task.getRelaDOList());
        return allTaskRelaDOs;
    }


}
