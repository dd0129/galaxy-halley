package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.Const;
import com.dianping.data.warehouse.core.common.HalleyConst;
import com.dianping.data.warehouse.core.dao.InstanceDAO;
import com.dianping.data.warehouse.core.dao.TaskDAO;
import com.dianping.data.warehouse.core.lion.LionUtil;
import com.dianping.data.warehouse.halley.domain.*;
import com.dianping.data.warehouse.halley.service.InstanceService;
import com.dianping.pigeon.remoting.provider.config.annotation.Service;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sunny on 14-7-3.
 */
@Service
@Transactional
public class InstanceServiceImpl implements InstanceService {

    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Resource
    private InstanceDAO instDAO;

    @Resource
    private TaskDAO taskDAO;

    public void setInstDAO(InstanceDAO instDAO) {
        this.instDAO = instDAO;
    }

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }


    /**
     * 按照日期进行任务查询，查询所有符合条件的任务以及其子任务直至叶子节点
     * 调用前请确保对InstanceQueryDO的格式进了检测，且startTime==endTime
     * 如果查询失败返回空list
     */
    @Override
    public List<InstanceDisplayDO> queryInstancesByDate(InstanceQueryDO instanceQueryDO) {
        logger.info("query instances by date, date: " + instanceQueryDO.getStartTime());
        if (!instanceQueryDO.getDependencyIsShow())
            return instDAO.getInstancesByInstanceQueryDO(instanceQueryDO);
        else {
            //获得当日所有的instance
            List<InstanceDisplayDO> instanceDisplayDOsByDate = getInstancesByDate(instanceQueryDO.getStartTime());
            if (instanceDisplayDOsByDate.size() == 0)
                return new ArrayList<InstanceDisplayDO>();
            //获得符合条件的所有的instance
            List<InstanceDisplayDO> instanceDisplayDOsByInstanceQueryDO =
                    getInstancesByInstanceQueryDO(instanceQueryDO, instanceDisplayDOsByDate);
            //获得当日所有的taskrela
            Map<String, List<InstanceDisplayDO>> instanceIdToInstancesMap = getInstancePostRelationsMap(instanceDisplayDOsByDate);
            //获得所有需要展示的instance
            List<InstanceDisplayDO> allInstancesNeedToDisplay = getAllRelatedInstancesByPost(instanceDisplayDOsByInstanceQueryDO,
                    instanceIdToInstancesMap);
            //设置instance之间的关系
            List<InstanceDisplayDO> instances = setInstanceRelationByPostRelation(allInstancesNeedToDisplay, instanceIdToInstancesMap);
            return instances;
        }
    }

    /**
     * 获得指定日期所有的instance，日期格式为YYYY-MM-DD
     */
    private List<InstanceDisplayDO> getInstancesByDate(String date) {
        return instDAO.getInstancesByDate(date);
    }

    /**
     * 获得符合条件的所有的instance
     */
    private List<InstanceDisplayDO> getInstancesByInstanceQueryDO(InstanceQueryDO instanceQueryDO,
                                                                  List<InstanceDisplayDO> instanceDisplayDOsByDate) {
        List<InstanceDisplayDO> instanceDisplayDOsByInstanceQueryDO = new ArrayList<InstanceDisplayDO>();
        for (InstanceDisplayDO instanceDisplayDO : instanceDisplayDOsByDate) {
            if (instanceDisplayDO.fitQuery(instanceQueryDO))
                instanceDisplayDOsByInstanceQueryDO.add(instanceDisplayDO);
        }
        return instanceDisplayDOsByInstanceQueryDO;
    }

    /**
     * 获得instance列表中所有相关的task_rela，即从父instanceId到子instance list的映射
     */
    private Map<String, List<InstanceDisplayDO>> getInstancePostRelationsMap(List<InstanceDisplayDO> instanceDisplayDOs) {
        List<Map<String, Object>> instanceIdToInstanceId = getInstanceIdToInstanceIdMap(instanceDisplayDOs);
        Map<String, List<InstanceDisplayDO>> instanceIdToInstances = getInstanceIdToPostInstancesMap(instanceDisplayDOs, instanceIdToInstanceId);
        return instanceIdToInstances;
    }

    /**
     * 获得instance列表中所有相关的task_rela，即从子instanceId到父instance list的映射
     */
    private Map<String, List<InstanceDisplayDO>> getInstancePreRelationsMap(List<InstanceDisplayDO> instanceDisplayDOs) {
        List<Map<String, Object>> instanceIdToInstanceId = getInstanceIdToInstanceIdMap(instanceDisplayDOs);
        Map<String, List<InstanceDisplayDO>> instanceIdToInstances = getInstanceIdToPreInstancesMap(instanceDisplayDOs, instanceIdToInstanceId);
        return instanceIdToInstances;
    }

    /**
     * 获得instance列表中所有相关的task_rela，即从父instanceId到子instanceId的映射
     */
    private List<Map<String, Object>> getInstanceIdToInstanceIdMap(List<InstanceDisplayDO> instances) {
        List<String> allInstanceIds = new ArrayList<String>();
        for (InstanceDisplayDO instanceDisplayDO : instances)
            allInstanceIds.add(instanceDisplayDO.getTaskStatusId());
        List<Map<String, Object>> instanceIdRelations = instDAO.getInstanceRelationsByTaskStatusIds(allInstanceIds);
        return instanceIdRelations;
    }

    /**
     * 根据父instanceId到子instanceId的映射以及所有的instance获得父instanceId到子instance list的映射
     */
    private Map<String, List<InstanceDisplayDO>> getInstanceIdToPostInstancesMap(List<InstanceDisplayDO> instanceDisplayDOsByDate,
                                                                                 List<Map<String, Object>> instanceIdToInstanceIdMap) {
        Map<String, InstanceDisplayDO> instanceIdToInstanceMap = getInstanceMap(instanceDisplayDOsByDate);
        Map<String, List<InstanceDisplayDO>> instanceIdToInstancesMap = new HashMap<String, List<InstanceDisplayDO>>();
        for (InstanceDisplayDO instanceDisplayDO : instanceDisplayDOsByDate) {
            instanceIdToInstancesMap.put(instanceDisplayDO.getTaskStatusId(), new ArrayList<InstanceDisplayDO>());
        }
        for (Map<String, Object> instanceRelation : instanceIdToInstanceIdMap) {
            String preId = (String) instanceRelation.get("pre_sts_id");
            String id = (String) instanceRelation.get("task_status_id");
            if (instanceIdToInstanceMap.containsKey(preId) && instanceIdToInstanceMap.containsKey(id)) {
                instanceIdToInstancesMap.get(preId).add(instanceIdToInstanceMap.get(id));
            }
        }
        return instanceIdToInstancesMap;
    }

    /**
     * 根据父instanceId到子instanceId的映射以及所有的instance获得子instanceId到父instance list的映射
     */
    private Map<String, List<InstanceDisplayDO>> getInstanceIdToPreInstancesMap(List<InstanceDisplayDO> instanceDisplayDOsByDate,
                                                                                List<Map<String, Object>> instanceIdToInstanceIdMap) {
        Map<String, InstanceDisplayDO> instanceIdToInstanceMap = getInstanceMap(instanceDisplayDOsByDate);
        Map<String, List<InstanceDisplayDO>> instanceIdToInstancesMap = new HashMap<String, List<InstanceDisplayDO>>();
        for (InstanceDisplayDO instanceDisplayDO : instanceDisplayDOsByDate) {
            instanceIdToInstancesMap.put(instanceDisplayDO.getTaskStatusId(), new ArrayList<InstanceDisplayDO>());
        }
        for (Map<String, Object> instanceRelation : instanceIdToInstanceIdMap) {
            String preId = (String) instanceRelation.get("pre_sts_id");
            String id = (String) instanceRelation.get("task_status_id");
            if (instanceIdToInstanceMap.containsKey(preId) && instanceIdToInstanceMap.containsKey(id)) {
                instanceIdToInstancesMap.get(id).add(instanceIdToInstanceMap.get(preId));
            }
        }
        return instanceIdToInstancesMap;
    }

    /**
     * 向下递归查找儿子节点获得所有需要展示的instances
     */
    private List<InstanceDisplayDO> getAllRelatedInstancesByPost(List<InstanceDisplayDO> instances,
                                                                 Map<String, List<InstanceDisplayDO>> instanceRelations) {
        Set<String> extraInstanceIdSet = new HashSet<String>();
        Map<String, InstanceDisplayDO> instanceDisplayDONeedToDisplayMap = new HashMap<String, InstanceDisplayDO>();
        for (InstanceDisplayDO instanceDisplayDO : instances) {
            extraInstanceIdSet.add(instanceDisplayDO.getTaskStatusId());
            instanceDisplayDONeedToDisplayMap.put(instanceDisplayDO.getTaskStatusId(), instanceDisplayDO);
        }
        //循环获得所有的子节点的taskStatusId并加入taskStatusIdSet
        while (hasAndSetPostInstance(instanceDisplayDONeedToDisplayMap, instanceRelations)) ;
        List<InstanceDisplayDO> instanceDisplayDOs = new ArrayList<InstanceDisplayDO>();
        for (Map.Entry<String, InstanceDisplayDO> entry : instanceDisplayDONeedToDisplayMap.entrySet())
            instanceDisplayDOs.add(entry.getValue());
        return instanceDisplayDOs;
    }

    /**
     * 向上递归查找父节点获得所有需要展示的instances
     */
    private List<InstanceDisplayDO> getAllRelatedInstancesByPre(List<InstanceDisplayDO> instances,
                                                                Map<String, List<InstanceDisplayDO>> instanceRelations) {
        Set<String> extraInstanceIdSet = new HashSet<String>();
        Map<String, InstanceDisplayDO> instanceDisplayDONeedToDisplayMap = new HashMap<String, InstanceDisplayDO>();
        for (InstanceDisplayDO instanceDisplayDO : instances) {
            extraInstanceIdSet.add(instanceDisplayDO.getTaskStatusId());
            instanceDisplayDONeedToDisplayMap.put(instanceDisplayDO.getTaskStatusId(), instanceDisplayDO);
        }
        //循环获得所有的子节点的taskStatusId并加入taskStatusIdSet
        while (hasAndSetPreInstance(instanceDisplayDONeedToDisplayMap, instanceRelations)) ;
        List<InstanceDisplayDO> instanceDisplayDOs = new ArrayList<InstanceDisplayDO>();
        for (Map.Entry<String, InstanceDisplayDO> entry : instanceDisplayDONeedToDisplayMap.entrySet())
            instanceDisplayDOs.add(entry.getValue());
        return instanceDisplayDOs;
    }

    /**
     * 遍历instanceRelations的元素，查找是instance list的中某个instance的子节点又不在instances集合中的节点，
     * 将其插入到instances中，如果存在这样的元素返回true，否则否会false
     */
    private boolean hasAndSetPostInstance(Map<String, InstanceDisplayDO> instances, Map<String, List<InstanceDisplayDO>> instanceRelations) {
        boolean flag = false;
        for (Map.Entry<String, List<InstanceDisplayDO>> entry : instanceRelations.entrySet()) {
            String id = entry.getKey();
            List<InstanceDisplayDO> childInstances = entry.getValue();
            if (instances.containsKey(id)) {
                for (InstanceDisplayDO instanceDisplayDO : childInstances) {
                    if (!instances.containsKey(instanceDisplayDO.getTaskStatusId())) {
                        instances.put(instanceDisplayDO.getTaskStatusId(), instanceDisplayDO);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 遍历instanceRelations的元素，查找是instance list的中某个instance的父节点又不在instances集合中的节点，
     * 将其插入到instances中，如果存在这样的元素返回true，否则否会false
     */
    private boolean hasAndSetPreInstance(Map<String, InstanceDisplayDO> instances, Map<String, List<InstanceDisplayDO>> instanceRelations) {
        boolean flag = false;
        for (Map.Entry<String, List<InstanceDisplayDO>> entry : instanceRelations.entrySet()) {
            String id = entry.getKey();
            List<InstanceDisplayDO> childInstances = entry.getValue();
            if (instances.containsKey(id)) {
                for (InstanceDisplayDO instanceDisplayDO : childInstances) {
                    if (!instances.containsKey(instanceDisplayDO.getTaskStatusId())) {
                        instances.put(instanceDisplayDO.getTaskStatusId(), instanceDisplayDO);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 根据instances和instanceRelations设置instances中节点的相互关系，即如果instanceRelations中parent节点和child节点都
     * 属于instances则设置其父子关系
     */
    private List<InstanceDisplayDO> setInstanceRelationByPostRelation(List<InstanceDisplayDO> instances, Map<String, List<InstanceDisplayDO>> instanceRelations) {
        Map<String, InstanceDisplayDO> instanceIdToInstanceMap = getInstanceMap(instances);
        for (Map.Entry<String, List<InstanceDisplayDO>> entry : instanceRelations.entrySet()) {
            String preId = entry.getKey();
            List<InstanceDisplayDO> childInstances = entry.getValue();
            for (InstanceDisplayDO instanceDisplayDO : childInstances) {
                String id = instanceDisplayDO.getTaskStatusId();
                if (instanceIdToInstanceMap.containsKey(preId) && instanceIdToInstanceMap.containsKey(id)) {
                    instanceIdToInstanceMap.get(preId).getChildren().add(id);
                }
            }
        }
        return instances;
    }

    /**
     * 根据instances和instanceRelations设置instances中节点的相互关系，即如果instanceRelations中parent节点和child节点都
     * 属于instances则设置其父子关系
     */
    private List<InstanceDisplayDO> setInstanceRelationByPreRelation(List<InstanceDisplayDO> instances, Map<String, List<InstanceDisplayDO>> instanceRelations) {
        Map<String, InstanceDisplayDO> instanceIdToInstanceMap = getInstanceMap(instances);
        for (Map.Entry<String, List<InstanceDisplayDO>> entry : instanceRelations.entrySet()) {
            String id = entry.getKey();
            List<InstanceDisplayDO> preInstances = entry.getValue();
            for (InstanceDisplayDO instanceDisplayDO : preInstances) {
                String preId = instanceDisplayDO.getTaskStatusId();
                if (instanceIdToInstanceMap.containsKey(preId) && instanceIdToInstanceMap.containsKey(id)) {
                    instanceIdToInstanceMap.get(preId).getChildren().add(id);
                }
            }
        }
        return instances;
    }

    /**
     * 按照时间段进行任务查询，查询所有符合条件的任务
     * 调用前请确保对InstanceQueryDO的格式进了检测，且startTime != endTime
     * 如果查询失败返回空list
     */
    @Override
    public List<InstanceDisplayDO> queryInstancesByInterval(InstanceQueryDO instanceQueryDO) {
        logger.info("query instances by interval, " + instanceQueryDO.getStartTime() + " ~ " + instanceQueryDO.getEndTime());
        //所有符合查询条件的节点的taskStatusId
        List<InstanceDisplayDO> instanceDisplayDOs = instDAO.getInstancesByInstanceQueryDO(instanceQueryDO);
        return instanceDisplayDOs;
    }

    /**
     * 任务详细信息
     * 如果查询失败返回null
     */
    @Override
    public InstanceDisplayDO getInstanceByInstanceId(String taskStatusId) {
        logger.info("get instance by instanceId, instanceId: " + taskStatusId);
        return instDAO.getInstanceByTaskStatusId(taskStatusId);
    }

    /**
     * 任务重跑，将任务状态置为init
     * 返回状态受影响的instance数
     */
    @Override
    public int recallInstance(String taskStatusId) {
        logger.info("recallInstance, instanceId: " + taskStatusId);
        return instDAO.recall(taskStatusId);
    }

    /**
     * 置为挂起，将任务状态置为suspend
     * 返回状态受影响的instance数
     */
    @Override
    public int suspendInstance(String taskStatusId) {
        logger.info("suspendInstance, instanceId: " + taskStatusId);
        return instDAO.suspend(taskStatusId);
    }

    /**
     * 置为成功，将任务状态置为success
     * 返回状态受影响的instance数
     */
    @Override
    public int successInstance(String taskStatusId) {
        logger.info("successInstance, instanceId: " + taskStatusId);
        return instDAO.success(taskStatusId);
    }

    /**
     * 快速通道，将任务的running_prio置为400
     * 返回状态受影响的instance数
     */
    @Override
    public int raisePriorityInstance(String taskStatusId) {
        logger.info("raise priority instance, instanceId: " + taskStatusId);
        return instDAO.raisePriority(taskStatusId);
    }

    /**
     * 停止预跑，将任务状态置为挂起
     * 返回状态受影响的instance数
     */
    @Override
    public int batchStopTask(String taskId) {
        logger.info("batch stop task, taskId: " + taskId);
        return instDAO.batchStop(taskId);
    }

    /**
     * 直接依赖，任务的直接父节点和直接子节点
     * 如果查询失败返回空list
     */
    @Override
    public List<InstanceDisplayDO> queryDirectRelation(String taskStatusId) {
        logger.info("query direct relation, instanceId: " + taskStatusId);
        //获得所有与instanceId直接相关的instance relation
        List<Map<String, Object>> instanceRelations = instDAO.getInstanceDirectRelationsByInstanceId(taskStatusId);
        //获得所有相关的instancesIds
        List<String> taskStatusIds = getRelatedInstances(taskStatusId, instanceRelations);
        //获得所有相关的instances
        List<InstanceDisplayDO> instanceDisplayDOs = instDAO.getInstancesByTaskStatusIds(taskStatusIds);
        instanceDisplayDOs = setInstanceRelation(instanceDisplayDOs, instanceRelations);
        return instanceDisplayDOs;
    }

    /**
     * 根据instanceId和instance relations获得所有与instanceId相关的instanceIds
     */
    private List<String> getRelatedInstances(String taskStatusId, List<Map<String, Object>> instanceRelations) {
        Set<String> taskStatusIdSet = new HashSet<String>();
        taskStatusIdSet.add(taskStatusId);
        for (Map<String, Object> instanceRelation : instanceRelations) {
            String preId = (String) instanceRelation.get("pre_sts_id");
            String id = (String) instanceRelation.get("task_status_id");
            if (taskStatusIdSet.contains(preId) && !taskStatusIdSet.contains(id))
                taskStatusIdSet.add(id);
            if (taskStatusIdSet.contains(id) && !taskStatusIdSet.contains(preId))
                taskStatusIdSet.add(preId);
        }
        return new ArrayList<String>(taskStatusIdSet);
    }

    /**
     * 根据instances和instanceRelations设置instances中节点的相互关系，即如果instanceRelations中parent节点和child节点都
     * 属于instances则设置其父子关系
     */
    private List<InstanceDisplayDO> setInstanceRelation(List<InstanceDisplayDO> instances, List<Map<String, Object>> instanceRelations) {
        Map<String, InstanceDisplayDO> instanceMap = getInstanceMap(instances);
        for (Map<String, Object> instanceRelation : instanceRelations) {
            String preId = (String) instanceRelation.get("pre_sts_id");
            String id = (String) instanceRelation.get("task_status_id");
            if (instanceMap.containsKey(preId) && instanceMap.containsKey(id)) {
                instanceMap.get(preId).getChildren().add(id);
                instanceMap.get(id).getParents().add(preId);
            }
        }
        return instances;
    }

    /**
     * 所有依赖，任务的所有后继节点直至叶子节点，所有的前驱节点直至根节点
     * 如果查询失败返回空list
     */
    @Override
    public List<InstanceDisplayDO> queryAllRelation(String taskStatusId) {
        logger.info("query all relation, instanceId: " + taskStatusId);
        //获得instance
        InstanceDisplayDO instanceDisplayDO = instDAO.getInstanceByTaskStatusId(taskStatusId);
        if (instanceDisplayDO == null)
            return new ArrayList<InstanceDisplayDO>();
        String date = instanceDisplayDO.getTimeId();
        //获得当日所有的instance
        List<InstanceDisplayDO> instanceDisplayDOsByDate = getInstancesByDate(date);
        //初始的instance
        List<InstanceDisplayDO> initInstanceDisplayDOs = new ArrayList<InstanceDisplayDO>();
        initInstanceDisplayDOs.add(instanceDisplayDO);
        //获得当日所有的taskrela
        Map<String, List<InstanceDisplayDO>> instanceIdToPostInstancesMap = getInstancePostRelationsMap(instanceDisplayDOsByDate);
        Map<String, List<InstanceDisplayDO>> instanceIdToPreInstancesMap = getInstancePreRelationsMap(instanceDisplayDOsByDate);
        //获得所有需要展示的子instance
        List<InstanceDisplayDO> allInstancesNeedToDisplay = getAllRelatedInstancesByPost(initInstanceDisplayDOs, instanceIdToPostInstancesMap);
        //获得所有需要展示的instance
        allInstancesNeedToDisplay = getAllRelatedInstancesByPre(allInstancesNeedToDisplay, instanceIdToPreInstancesMap);
        //设置instance之间的关系
        List<InstanceDisplayDO> instances = setInstanceRelationByPostRelation(allInstancesNeedToDisplay, instanceIdToPostInstancesMap);
        return instances;
    }

    /**
     * 返回instanceId到instance本身的map
     */
    private Map<String, InstanceDisplayDO> getInstanceMap(List<InstanceDisplayDO> instanceDisplayDOs) {
        Map<String, InstanceDisplayDO> instanceIdToInstanceMap = new HashMap<String, InstanceDisplayDO>();
        for (InstanceDisplayDO instanceDisplayDO : instanceDisplayDOs)
            instanceIdToInstanceMap.put(instanceDisplayDO.getTaskStatusId(), instanceDisplayDO);
        return instanceIdToInstanceMap;
    }

    /**
     * 根据instanceId返回其自身以及所有直接和间接依赖其的节点
     */
    @Override
    public List<InstanceDisplayDO> getAllPostInstancesByInstanceId(String instanceId) {
        logger.info("get all post instances by instanceId, instanceId: " + instanceId);
        InstanceDisplayDO instanceDisplayDO = instDAO.getInstanceByTaskStatusId(instanceId);
        //获得当日所有的instance
        List<InstanceDisplayDO> instanceDisplayDOsByDate = getInstancesByDate(instanceDisplayDO.getTimeId());
        if (instanceDisplayDOsByDate.size() == 0)
            return new ArrayList<InstanceDisplayDO>();
        //获得符合条件的所有的instance
        List<InstanceDisplayDO> instanceDisplayDOsByInstanceQueryDO = new ArrayList<InstanceDisplayDO>();
        instanceDisplayDOsByInstanceQueryDO.add(instanceDisplayDO);
        //获得当日所有的taskrela
        Map<String, List<InstanceDisplayDO>> instanceIdToInstancesMap = getInstancePostRelationsMap(instanceDisplayDOsByDate);
        //获得所有需要展示的instance
        List<InstanceDisplayDO> allInstancesNeedToDisplay = getAllRelatedInstancesByPost(instanceDisplayDOsByInstanceQueryDO,
                instanceIdToInstancesMap);
        //设置instance之间的关系
        List<InstanceDisplayDO> instances = setInstanceRelationByPostRelation(allInstancesNeedToDisplay, instanceIdToInstancesMap);
        return instances;
    }

    /**
     * 根据instanceId返回其自身以及其所有直接和间接依赖的节点
     */
    @Override
    public List<InstanceDisplayDO> getAllPreInstancesByInstanceId(String instanceId) {
        logger.info("get all pre instances by instanceId, instanceId: " + instanceId);
        InstanceDisplayDO instanceDisplayDO = instDAO.getInstanceByTaskStatusId(instanceId);
        //获得当日所有的instance
        List<InstanceDisplayDO> instanceDisplayDOsByDate = getInstancesByDate(instanceDisplayDO.getTimeId());
        if (instanceDisplayDOsByDate.size() == 0)
            return new ArrayList<InstanceDisplayDO>();
        //获得符合条件的所有的instance
        List<InstanceDisplayDO> instanceDisplayDOsByInstanceQueryDO = new ArrayList<InstanceDisplayDO>();
        instanceDisplayDOsByInstanceQueryDO.add(instanceDisplayDO);
        //获得当日所有的taskrela
        Map<String, List<InstanceDisplayDO>> instanceIdToInstancesMap = getInstancePreRelationsMap(instanceDisplayDOsByDate);
        //获得所有需要展示的instance
        List<InstanceDisplayDO> allInstancesNeedToDisplay = getAllRelatedInstancesByPre(instanceDisplayDOsByInstanceQueryDO,
                instanceIdToInstancesMap);
        //设置instance之间的关系
        List<InstanceDisplayDO> instances = setInstanceRelationByPostRelation(allInstancesNeedToDisplay, instanceIdToInstancesMap);
        return instances;
    }

    /**
     * 获取指定path的日志
     */
    @Override
    public String getInstanceLog(String logPath) {
        logger.info("get instance log, logPath: " + logPath);
        String logUrl = LionUtil.getProperty(HalleyConst.LION_LOG_URL);
        logPath = logPath.replace(HalleyConst.logDir, logUrl);
        try {
            URL url = new URL(logPath);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "UTF-8"
            ));
            StringBuilder logContent = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                logContent.append(line).append("<br>");
            }
            return logContent.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 获取级联重跑所有需要重跑的instance
     */
    @Override
    public List<InstanceChildrenDO> getAllChildrenOfQueryInstanceId(String instanceId, String date) {
        logger.info("get all children of query instanceId, instanceId: " + instanceId + ", date: " + date);

        long startTime = System.currentTimeMillis();

        if (null == date) {
            date = new DateTime().toString("yyyy-MM-dd");
        }
        List<TaskChildToParentDO> taskChildToParentDOList = this.getChildToParent(date);

        Map<String, Integer> idToStatusMap = new HashMap<String, Integer>();
        Map<String, InstanceInfoForCascadeDO> idToNameMap = new HashMap<String, InstanceInfoForCascadeDO>();

        for (TaskChildToParentDO taskChildToParentDO : taskChildToParentDOList) {
            if (null == idToNameMap.get(taskChildToParentDO.getChildId())) {
                InstanceInfoForCascadeDO instanceInfoForCascadeDO = new InstanceInfoForCascadeDO();
                instanceInfoForCascadeDO.setTaskName(taskChildToParentDO.getChildTaskName());
                instanceInfoForCascadeDO.setTaskID(taskChildToParentDO.getChildText()); //task_id
                idToNameMap.put(taskChildToParentDO.getChildId(), instanceInfoForCascadeDO);
                idToStatusMap.put(taskChildToParentDO.getChildId(), taskChildToParentDO.getChildStatus());
            }
            if (null == idToNameMap.get(taskChildToParentDO.getParentId())) {
                InstanceInfoForCascadeDO instanceInfoForCascadeDO = new InstanceInfoForCascadeDO();
                instanceInfoForCascadeDO.setTaskName(taskChildToParentDO.getParentTaskName());
                instanceInfoForCascadeDO.setTaskID(taskChildToParentDO.getParentText()); //task_id
                idToNameMap.put(taskChildToParentDO.getParentId(), instanceInfoForCascadeDO);
                idToStatusMap.put(taskChildToParentDO.getParentId(), taskChildToParentDO.getParentStatus());
            }
        }
        Map<String, List<String>> idToChildrenMap = this.getTaskIdToChildMap(taskChildToParentDOList);

        List<InstanceChildrenDO> cascadedJobs = new ArrayList<InstanceChildrenDO>();

        if (!idToStatusMap.containsKey(instanceId)) {
            List<String> ids = new ArrayList<String>();
            ids.add(instanceId);
            InstanceDisplayDO inst = instDAO.getInstancesByTaskStatusIds(ids).get(0);

            InstanceChildrenDO instanceChildrenDO = new InstanceChildrenDO();
            instanceChildrenDO.setId(instanceId);
            instanceChildrenDO.setpId("0");
            instanceChildrenDO.setTaskName(inst.getTaskName());
            instanceChildrenDO.setTaskId(String.valueOf(inst.getTaskId()));
            cascadedJobs.add(instanceChildrenDO);
            return cascadedJobs;
        }
        if (this.isReRunAble(idToStatusMap.get(instanceId))) {
            InstanceChildrenDO instanceChildrenDO = new InstanceChildrenDO();
            instanceChildrenDO.setId(instanceId);
            instanceChildrenDO.setpId("0");
            instanceChildrenDO.setTaskName(idToNameMap.get(instanceId).getTaskName());
            instanceChildrenDO.setTaskId(idToNameMap.get(instanceId).getTaskID());
            cascadedJobs.add(instanceChildrenDO);
        }
        List<String> idList = new ArrayList<String>();
        idList.add(instanceId);
        while (idList.size() > 0) {
            String currentId = idList.remove(0);
            if (this.isReRunAble(idToStatusMap.get(currentId))) {
                List<String> cList = idToChildrenMap.get(currentId);
                if (cList != null) {
                    for (String childId : cList) {
                        if (this.isReRunAble(idToStatusMap.get(childId))) {
                            idList.add(childId);
                            InstanceChildrenDO instanceChildrenDO = new InstanceChildrenDO();
                            instanceChildrenDO.setId(childId);
                            instanceChildrenDO.setpId(currentId);
                            instanceChildrenDO.setTaskName(idToNameMap.get(childId).getTaskName());
                            instanceChildrenDO.setTaskId(idToNameMap.get(childId).getTaskID());
                            cascadedJobs.add(instanceChildrenDO);
                        }
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time of get children task is:" + (endTime - startTime) / 1000);
        return cascadedJobs;
    }

    /**
     * 级联重跑
     */
    @Override
    public Integer recallCascade(String startDate, String endDate, List<String> taskIds) {
        logger.info("recall cascade, startDate: " + startDate + ", endDate: " + endDate + ", taskIds: " + taskIds);
        //recallCascadeSendEmail(startDate, endDate, taskIds);
        return instDAO.reRunCascadeJobs(startDate, endDate, taskIds);
    }

    /**
     * 查询预跑的instances
     */
    @Override
    public List<HashMap<String, Object>> queryPreRunInstances(String preRunTime, String task_committer, String taskIDOrName) {
        logger.info("query prerun instances, preruntime: " + preRunTime + ", task_committer: " + task_committer +
                ", taskIDOrName: " + taskIDOrName);
        Integer taskID = null;
        String taskName = null;

        if (taskIDOrName.matches("\\d+")) {
            taskID = new Integer(taskIDOrName);
        } else {
            taskName = taskIDOrName;
        }

        List<HashMap<String, Object>> taskDOList = instDAO.queryPreRunInstances(preRunTime, task_committer, taskID, taskName);
        if (null == taskDOList)
            return new ArrayList<HashMap<String, Object>>();
        return taskDOList;
    }

    @Override
    public TaskOwnerDO getOncallOwner(Integer taskId) {
        logger.info("get oncall owner, taskId: " + taskId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InstanceDO inst = instDAO.getInstancesForOncall(taskId, sdf.format(new Date()));
        if (inst == null) {
            return null;
        }
        String responsible = instDAO.getExceptionResponsible(inst.getJobCode());
        if (responsible == null) {
            return instDAO.getOwnerByConditions(inst.getOwner(), null);
        } else if (responsible.equals("infrastructure") || responsible.equals("product")) {
            return instDAO.getOwnerByConditions(null, responsible);
        } else {
            return instDAO.getOwnerByConditions(inst.getOwner(), null);
        }

//        switch (inst.getStatus().intValue()){
//            case -1 :
//                String responsible = instDAO.getExceptionResponsible(inst.getJobCode());
//                if(responsible == null){
//                    return instDAO.getOwnerByConditions(inst.getOwner(),null);
//                }else if(responsible.equals("owner")){
//                    return instDAO.getOwnerByConditions(inst.getOwner(),null);
//                }else if(responsible.equals("infrastructure") || responsible.equals("product") ){
//                    return instDAO.getOwnerByConditions(null,responsible);
//                }else{
//                    throw new RuntimeException(responsible +" is illegal");
//                }
//            case 7 :
//                return instDAO.getOwnerByConditions(inst.getOwner(),null);
//            case 5 :
//                return instDAO.getOwnerByConditions(inst.getOwner(),null);
//            default:
//                return null;
//        }
    }

    /**
     * 任务实例状态分析
     */
    @Override
    public String instanceStatusAnalyze(String taskStatusId) {
        logger.info("instance status analyze, instanceId: " + taskStatusId);
        InstanceDisplayDO instanceDisplayDO = instDAO.getInstanceByTaskStatusId(taskStatusId);
        switch (instanceDisplayDO.getStatus()) {
            case Const.INSTANCE_STATUS_FAIL:
                return instanceFailAnalyze(instanceDisplayDO.getTaskStatusId());
            case Const.INSTANCE_STATUS_INIT:
                return instanceInitAnalyze(instanceDisplayDO.getTaskStatusId());
            case Const.INSTANCE_STATUS_SUCCESS:
                return "当前任务实例已经执行成功，如有疑问请联系开发平台管理员。";
            case Const.INSTANCE_STATUS_RUNNING:
                return "当前任务实例正在运行，请耐心等待，如有疑问请联系开发平台管理员。";
            case Const.INSTANCE_STATUS_SUSPEND:
                return "当前任务实例被挂起，如有疑问请联系开发平台管理员。";
            case Const.INSTANCE_STATUS_INITERROR:
                return "当前任务实例初始化失败，请联系开发平台管理员。";
            case Const.INSTANCE_STATUS_WAIT:
                return "当前任务实例的前置条件尚未完成，请耐心等待，如有疑问请联系开发平台管理员。";
            case Const.INSTANCE_STATUS_READY:
                return instanceReadyAnalyze(instanceDisplayDO.getTaskId());
            case Const.INSTANCE_STATUS_TIMEOUT:
                return "当前任务实例已超出最大运行时间，如有疑问请联系开发平台管理员。";
            default:
                return "当前任务实例状态为" + instanceDisplayDO.getStatus() + "，请联系开发平台管理员。";
        }
    }

    private List<InstanceChildrenDO> getAllChildrenOfQueryInstanceId_Refactor(String instanceId, String date) {

        long startTime = System.currentTimeMillis();

        if (null == date) {
            date = new DateTime().toString("yyyy-MM-dd");
        }
        List<TaskChildToParentDO> taskChildToParentDOList = this.getChildToParent_Refactor(date);

        Map<String, Integer> idToStatusMap = new HashMap<String, Integer>();
        Map<String, InstanceInfoForCascadeDO> idToNameMap = new HashMap<String, InstanceInfoForCascadeDO>();

        for (TaskChildToParentDO taskChildToParentDO : taskChildToParentDOList) {
            if (null == idToNameMap.get(taskChildToParentDO.getChildId())) {
                InstanceInfoForCascadeDO instanceInfoForCascadeDO = new InstanceInfoForCascadeDO();
                instanceInfoForCascadeDO.setTaskName(taskChildToParentDO.getChildTaskName());
                instanceInfoForCascadeDO.setTaskID(taskChildToParentDO.getChildText()); //task_id
                idToNameMap.put(taskChildToParentDO.getChildId(), instanceInfoForCascadeDO);
                idToStatusMap.put(taskChildToParentDO.getChildId(), taskChildToParentDO.getChildStatus());
            }
            if (null == idToNameMap.get(taskChildToParentDO.getParentId())) {
                InstanceInfoForCascadeDO instanceInfoForCascadeDO = new InstanceInfoForCascadeDO();
                instanceInfoForCascadeDO.setTaskName(taskChildToParentDO.getParentTaskName());
                instanceInfoForCascadeDO.setTaskID(taskChildToParentDO.getParentText()); //task_id
                idToNameMap.put(taskChildToParentDO.getParentId(), instanceInfoForCascadeDO);
                idToStatusMap.put(taskChildToParentDO.getParentId(), taskChildToParentDO.getParentStatus());
            }
        }
        Map<String, List<String>> idToChildrenMap = this.getTaskIdToChildMap(taskChildToParentDOList);

        List<InstanceChildrenDO> cascadedJobs = new ArrayList<InstanceChildrenDO>();
        if (this.isReRunAble(idToStatusMap.get(instanceId))) {
            InstanceChildrenDO instanceChildrenDO = new InstanceChildrenDO();
            instanceChildrenDO.setId(instanceId);
            instanceChildrenDO.setpId("0");
            instanceChildrenDO.setTaskName(idToNameMap.get(instanceId).getTaskName());
            instanceChildrenDO.setTaskId(idToNameMap.get(instanceId).getTaskID());
            cascadedJobs.add(instanceChildrenDO);
        }
        List<String> idList = new ArrayList<String>();
        idList.add(instanceId);
        while (idList.size() > 0) {
            String currentId = idList.remove(0);
            if (this.isReRunAble(idToStatusMap.get(currentId))) {
                List<String> cList = idToChildrenMap.get(currentId);
                if (cList != null) {
                    for (String childId : cList) {
                        if (this.isReRunAble(idToStatusMap.get(childId))) {
                            idList.add(childId);
                            InstanceChildrenDO instanceChildrenDO = new InstanceChildrenDO();
                            instanceChildrenDO.setId(childId);
                            instanceChildrenDO.setpId(currentId);
                            instanceChildrenDO.setTaskName(idToNameMap.get(childId).getTaskName());
                            instanceChildrenDO.setTaskId(idToNameMap.get(childId).getTaskID());
                            cascadedJobs.add(instanceChildrenDO);
                        }
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.print("Time of get children task is:" + (endTime - startTime) / 1000);
        return cascadedJobs;
    }

    private List<TaskChildToParentDO> getChildToParent_Refactor(String date) {
        long startTime = System.currentTimeMillis();
        List<HashMap<String, Object>> taskStatusField = instDAO.getTaskStatusFieldForCascade(date);
        String date_rela_str;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date_rela = dateFormat.parse(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date_rela);
            calendar.add(calendar.DATE, -1);
            calendar.set(calendar.HOUR_OF_DAY, 21);
            calendar.set(calendar.MINUTE, 0);
            calendar.set(calendar.SECOND, 0);
            date_rela = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date_rela_str = format.format(date_rela);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }

        List<HashMap<String, Object>> taskRelaStatusField = instDAO.getTaskRelaStatusFieldForCascade(date_rela_str);

        if (null == taskRelaStatusField || null == taskStatusField ||
                taskRelaStatusField.isEmpty() || taskStatusField.isEmpty()) {
            return null;
        }
        Map<String, Map<String, Object>> taskStatusMap_Key_Task_Status_Id_First = new HashMap<String, Map<String, Object>>();
        Map<String, Map<String, Object>> taskStatusMap_Key_Pre_Sts_Id_First_Union = new HashMap<String, Map<String, Object>>();
        Map<String, Map<String, Object>> taskStatusMap_Key_Task_Status_Id_Second = new HashMap<String, Map<String, Object>>();
        Map<String, Map<String, Object>> taskRelaStatusMap = new HashMap<String, Map<String, Object>>();

        for (HashMap<String, Object> map : taskStatusField) {
            taskStatusMap_Key_Task_Status_Id_First.put((String) map.get("child_id"), map);
            taskStatusMap_Key_Task_Status_Id_Second.put((String) map.get("child_id"), map);
        }
        for (HashMap<String, Object> map : taskRelaStatusField) {
            taskRelaStatusMap.put((String) map.get("task_status_id"), map);
        }
        for (String task_status_id : taskStatusMap_Key_Task_Status_Id_First.keySet()) {
            if (taskRelaStatusMap.containsKey(task_status_id)) {
                taskStatusMap_Key_Pre_Sts_Id_First_Union.put((String) taskRelaStatusMap.get(task_status_id).get("pre_sts_id"),
                        taskStatusMap_Key_Task_Status_Id_First.get(task_status_id));
            }
        }

        //taskStatusMap_Key_Pre_Sts_Id_First_Union as a,b child_xxx
        List<TaskChildToParentDO> taskChildToParentDOList = new LinkedList<TaskChildToParentDO>();
        for (String pre_sts_id : taskStatusMap_Key_Pre_Sts_Id_First_Union.keySet()) {
            if (taskStatusMap_Key_Task_Status_Id_First.containsKey(pre_sts_id)) {
                TaskChildToParentDO taskChildToParentDO = new TaskChildToParentDO();
                if (null != taskStatusMap_Key_Task_Status_Id_First.get(pre_sts_id).get("child_end_time")) {
                    taskChildToParentDO.setParentEndTime(
                            (Long) taskStatusMap_Key_Task_Status_Id_First.get(pre_sts_id).get("child_end_time")
                    );
                }
                taskChildToParentDO.setParentId((String) taskStatusMap_Key_Task_Status_Id_First.get(pre_sts_id).get("child_id"));
                taskChildToParentDO.setParentStatus(
                        (Integer) taskStatusMap_Key_Task_Status_Id_First.get(pre_sts_id).get("child_status")
                );
                taskChildToParentDO.setParentTaskName((String) taskStatusMap_Key_Task_Status_Id_First.get(pre_sts_id).get("child_task_name"));
                taskChildToParentDO.setParentText(taskStatusMap_Key_Task_Status_Id_First.get(pre_sts_id).get("child_text").toString());

                if (null != taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("child_end_time"))
                    taskChildToParentDO.setChildEndTime(
                            (Long) taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("child_end_time")
                    );
                taskChildToParentDO.setChildId((String) taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("child_id"));
                taskChildToParentDO.setChildStatus(
                        (Integer) taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("child_status")
                );
                taskChildToParentDO.setChildTaskName((String) taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("child_task_name"));
                taskChildToParentDO.setChildText(taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("child_text").toString());
                taskChildToParentDO.setTimeId((String) taskStatusMap_Key_Pre_Sts_Id_First_Union.get(pre_sts_id).get("time_id"));
                taskChildToParentDOList.add(taskChildToParentDO);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.print("Time of get children task is:" + (endTime - startTime) / 1000);
        return taskChildToParentDOList;
    }

    private Map<String, List<String>> getTaskIdToChildMap(List<TaskChildToParentDO> childToParentDOs) {
        Map<String, List<String>> idToChildMap = new HashMap<String, List<String>>();
        for (TaskChildToParentDO entity : childToParentDOs) {
            String pId = entity.getParentId();
            if (null != pId) {
                List<String> cList = idToChildMap.get(pId);
                if (null == cList) {
                    cList = new ArrayList<String>();
                    idToChildMap.put(pId, cList);
                }
                cList.add(entity.getChildId());
            }
        }
        return idToChildMap;
    }

    private boolean isReRunAble(Integer status) {
        return status == HalleyConst.TASK_STATUS_FAIL
                || status == HalleyConst.TASK_STATUS_SUCCESS
                || status == HalleyConst.TASK_STATUS_SUSPEND
                || status == HalleyConst.TASK_STATUS_WAIT;
    }

    private List<TaskChildToParentDO> getChildToParent(String date) {
        long startTime = System.currentTimeMillis();
        List<TaskChildToParentDO> result = instDAO.getChildToParent(date);
        long endTime = System.currentTimeMillis();
        System.out.print("Time of get children task is:" + (endTime - startTime) / 1000);
        return result;
    }


    /**
     * 级联重跑发送邮件通知owner
     */
    private void recallCascadeSendEmail(String startDate, String endDate, List<String> taskIds) {
        Map<String, List<TaskDO>> ownerToTaskIds = getOwerToTaskIdsMap(taskIds);
        for (Map.Entry<String, List<TaskDO>> entry : ownerToTaskIds.entrySet()) {
            String owner = entry.getKey();
            String address = owner + "@dianping.com";
            List<TaskDO> taskDOs = entry.getValue();
            String mailContent = getMailContent(startDate, endDate, taskDOs);
            sendEmail(mailContent, address);
        }
    }

    /**
     * 将taskIds转换成owner到taskDOs的map
     */
    private Map<String, List<TaskDO>> getOwerToTaskIdsMap(List<String> taskIds) {
        Map<String, List<TaskDO>> ownerToTasks = new HashMap<String, List<TaskDO>>();
        for (int i = 0; i < taskIds.size(); i++) {
            String taskId = taskIds.get(i);
            TaskDO taskDO = taskDAO.getTaskByTaskId(Integer.parseInt(taskId));
            String owner = taskDO.getOwner();
            if (ownerToTasks.containsKey(owner)) {
                ownerToTasks.get(owner).add(taskDO);
            } else {
                List<TaskDO> taskDOs = new ArrayList<TaskDO>();
                taskDOs.add(taskDO);
                ownerToTasks.put(owner, taskDOs);
            }
        }
        return ownerToTasks;
    }

    /**
     * 根据startDate、endDate以及重跑的taskDO组织邮件内容
     */
    private String getMailContent(String startDate, String endDate, List<TaskDO> taskDOs) {
        String content = "";
        String timeContent = "";
        if (startDate.equals(endDate))
            timeContent = startDate;
        else
            timeContent = startDate + "至" + endDate;
        for (int i = 0; i < taskDOs.size(); i++) {
            TaskDO taskDO = taskDOs.get(i);
            Integer taskId = taskDO.getTaskId();
            String taskName = taskDO.getTaskName();
            content += "任务(" + taskId + "," + taskName + ")" + "在时间(" + timeContent + ")的实例被级联重跑\n";
        }
        content += "\n以上操作通常由开发平台管理员发起，如有疑问，请联系开发平台管理员";
        return content;
    }

    /**
     * 向邮箱mailAddresses发送内容content
     */
    private boolean sendEmail(String content, String mailAddresses) {
        Properties props = new Properties();
        props.put(Const.MAIL_SMTP_HOST_LABEL, Const.MAIL_SMTP_HOST);
        props.put(Const.MAIL_SMTP_AUTH_LABEL, Const.MAIL_SMTP_AUTH);
        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(Const.MAIL_51PING));
            msg.addRecipients(Message.RecipientType.TO, mailAddresses);
            msg.setSubject(Const.MAIL_SUBJECT);
            msg.setSentDate(new Date());
            msg.setContent(content, "text/plain;charset=utf8");
            Transport.send(msg);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 分析任务fail的原因
     */
    private String instanceFailAnalyze(String instanceId) {
        TaskOwnerDO taskOwnerDO = getOncallOwner(instanceId);
        return "当前任务实例执行失败，详细信息参见日志，如有疑问请联系责任人(" + taskOwnerDO.getMail() + ")或开发平台管理员。";
    }

    /**
     * 分析任务一直init的原因
     */
    private String instanceInitAnalyze(String instanceId) {
        InstanceDisplayDO instanceDisplayDO = instanceInitAnalyzeByPreInstances(instanceId);
        if (instanceDisplayDO != null)
            return "当前任务实例的上游任务: " + instanceDisplayDO.getTaskId() + "未执行成功，如有疑问请联系开发平台管理员。";
        else
            return "该任务实例尚未到达开始执行时间，请耐心等待，如有疑问请联系开发平台管理员。";
    }

    /**
     * 分析任务一直ready的原因
     */
    private String instanceReadyAnalyze(int taskId) {
        InstanceDisplayDO instanceDisplayDO = instanceReadyAnalyzeByInstancesWithSameTaskId(taskId);
        if (instanceDisplayDO != null)
            return "当前任务的另外一个实例" + instanceDisplayDO.getTaskStatusId() + "正在运行，请耐心等待，如有疑问请联系开发平台管理员。";
        else
            return "当前任务实例正在等待调度资源，请耐心等待，如有疑问请联系开发平台管理员。";
    }

    /**
     * 通过上游任务实例的状态分析任务一直init的原因。
     * 如果存在则返回造成任务一直init的任务实例，否则返回null
     */
    private InstanceDisplayDO instanceInitAnalyzeByPreInstances(String instanceId) {
        List<String> preInstanceIds = instDAO.getDirectPreInstanceIdsByInstanceId(instanceId);
        for (String preInstanceId : preInstanceIds) {
            InstanceDisplayDO instanceDisplayDO = instDAO.getInstanceByTaskStatusId(preInstanceId);
            if (instanceDisplayDO.getStatus() != 1)
                return instanceDisplayDO;
        }
        return null;
    }

    /**
     * 通过相同task_id的任务实例分析任务一直ready的原因。
     * 如果存在则返回造成任务一直ready的任务实例，否则返回null
     */
    private InstanceDisplayDO instanceReadyAnalyzeByInstancesWithSameTaskId(int taskId) {
        List<InstanceDisplayDO> instanceDisplayDOs = instDAO.getInstancesByStatus(Const.INSTANCE_STATUS_RUNNING);
        for (InstanceDisplayDO instanceDisplayDO : instanceDisplayDOs) {
            if (instanceDisplayDO.getTaskId() == taskId)
                return instanceDisplayDO;
        }
        return null;
    }

    private TaskOwnerDO getOncallOwner(String instanceId) {
        InstanceDO inst = instDAO.getInstanceForOncallByTaskStatusId(instanceId);
        if (inst == null) {
            return null;
        }
        String responsible = instDAO.getExceptionResponsible(inst.getJobCode());
        if (responsible == null) {
            return instDAO.getOwnerByConditions(inst.getOwner(), null);
        } else if (responsible.equals("infrastructure") || responsible.equals("product") ||
                responsible.equals("yix.zhang")) {
            return instDAO.getOwnerByConditions(null, responsible);
        } else {
            return instDAO.getOwnerByConditions(inst.getOwner(), null);
        }
    }

    /**
     * 给定instanceId获取最长路径
     */
    @Override
    public List<InstanceDisplayDO> getLongestPath(String instanceId) {
        logger.info("get longest path, instanceId: " + instanceId);
        //获得instance
        InstanceDisplayDO instanceDisplayDO = instDAO.getInstanceByTaskStatusId(instanceId);
        if (instanceDisplayDO == null)
            return new ArrayList<InstanceDisplayDO>();
        String date = instanceDisplayDO.getTimeId();
        //获得当日所有的instance
        List<InstanceDisplayDO> instanceDisplayDOsByDate = getInstancesByDate(date);
        //初始的instance
        List<InstanceDisplayDO> initInstanceDisplayDOs = new ArrayList<InstanceDisplayDO>();
        initInstanceDisplayDOs.add(instanceDisplayDO);
        //获得当日所有的taskrela
        Map<String, List<InstanceDisplayDO>> instanceIdToPreInstancesMap = getInstancePreRelationsMap(instanceDisplayDOsByDate);
        InstanceDisplayDO currentInstance = instanceDisplayDO;
        while (true) {
            List<InstanceDisplayDO> preInstances = instanceIdToPreInstancesMap.get(currentInstance.getTaskStatusId());
            if (preInstances == null || preInstances.isEmpty())
                break;
            currentInstance = getLastSuccessInstance(preInstances);
            if (currentInstance == null)
                break;
            initInstanceDisplayDOs.add(currentInstance);
        }
        //设置instance之间的关系
        List<InstanceDisplayDO> instances = setInstanceRelationByPreRelation(initInstanceDisplayDOs, instanceIdToPreInstancesMap);
        return instances;
    }

    private InstanceDisplayDO getLastSuccessInstance(List<InstanceDisplayDO> instanceDisplayDOs) {
        Date lastSuccessTime = null;
        InstanceDisplayDO lastSuccessInstance = null;
        for (InstanceDisplayDO instanceDisplayDO : instanceDisplayDOs) {
            Date successTime = string2Date(instanceDisplayDO.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            if (successTime == null)
                continue;
            if (lastSuccessInstance == null) {
                lastSuccessTime = successTime;
                lastSuccessInstance = instanceDisplayDO;
            } else {
                if (successTime.after(lastSuccessTime)) {
                    lastSuccessTime = successTime;
                    lastSuccessInstance = instanceDisplayDO;
                }
            }
        }
        return lastSuccessInstance;
    }

    private Date string2Date(String timeStamp, String format) {
        if (timeStamp == null)
            return null;
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(timeStamp);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
