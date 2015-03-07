package com.dianping.data.warehouse.core.common;

import com.dianping.data.warehouse.halley.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskTestUtils {

    public static TaskDO createExpectTaskDO(List<TaskRelaDO> relaDOList) {
        TaskDO expect = new TaskDO();
        expect.setAddTime("2012-05-09 12:16:34.000000");
        expect.setAddUser("hongdi.tang");
        expect.setCycle("D");
        expect.setDatabaseSrc("mysql_DianPingBI");
        expect.setFreq("10 5 0 * * ?");
        expect.setIfPre(0);
        expect.setIfRecall(1);
        expect.setIfVal(1);
        expect.setIfWait(1);
        expect.setLogFile("bi_data_tg_bussiness_city");
        expect.setLogHome("${wormhole_log_home}/wormhole");
        expect.setOffset("D0");
        expect.setOffsetType("offset");
        expect.setOwner("yifan.cao");
        expect.setPara1("\\\"10001 mysql greenplum \\\"");
        expect.setPara2("\\\"/data/deploy/dwarch/conf/ETL/job/auto_etl/tuangou/BI_Data_TG_Bussiness_City.xml\\\"");
        expect.setPara3("\\\" \\\"  \\\"${task_id}\\\" \\\"${cal_dt}\\\"");
        expect.setPrioLvl(3);
        expect.setRecallCode("203;305;605;22;300;1;255;301;600;601;602;603;607;606;254;604;306");
        expect.setRecallInterval(10);
        expect.setRecallLimit(10);
        expect.setRemark("test");
        expect.setSuccessCode("0;201");
        expect.setTableName("bi_data_tg_bussiness_city");
        expect.setTaskGroupId(1);
        expect.setTaskId(10001);
        expect.setTaskName("mysql2gp##BI_Data_TG_Bussiness_City");
        expect.setTaskObj("ssh -o ConnectTimeout=3 -o ConnectionAttempts=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no -p 58422 deploy@10.1.6.49 sh /data/deploy/dwarch/conf/ETL/bin/start_autoetl.sh");
        expect.setTimeout(90);
        expect.setType(1);
        expect.setUpdateTime("2013-08-27 15:10:36.000000");
        expect.setUpdateUser("hongdi.tang");
        expect.setWaitCode("302");
        expect.setRelaDOList(relaDOList);
        return expect;
    }

    public static TaskDO createExpectTaskDOForGetAll(List<TaskRelaDO> relaDOList) {
        TaskDO expect = new TaskDO();
        expect.setTaskObj("ssh -o ConnectTimeout=3 -o ConnectionAttempts=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no -p 58422 deploy@10.1.6.49 sh /data/deploy/dwarch/conf/ETL/bin/start_autoetl.sh");
        expect.setTaskId(10001);
        return expect;
    }

    public static List<TaskRelaDO> createExpectedTaskRelaList() {
        List<TaskRelaDO> expect = new ArrayList<TaskRelaDO>();
        TaskRelaDO do1 = createTaskRelaDO(10001, "mysql2gp##BI_Main_ActiveUser_Daily", 10002,
                "2014-02-13 18:41:59.000000", "yifan.cao", "D0");
        TaskRelaDO do2 = createTaskRelaDO(10001, "mysql2gp##CI_CheckIn", 10003,
                "2014-02-13 18:41:59.000000", "yifan.cao", "D0");
        expect.add(do1);
        expect.add(do2);
        return expect;
    }

    private static TaskRelaDO createTaskRelaDO(Integer taskId, String taskName, Integer taskPreId,
                                               String timeStamp, String owner, String cycleGap) {
        TaskRelaDO taskRelaDo = new TaskRelaDO();
        taskRelaDo.setTaskId(taskId);
        taskRelaDo.setTaskName(taskName);
        taskRelaDo.setTaskPreId(taskPreId);
        taskRelaDo.setTimeStamp(timeStamp);
        taskRelaDo.setOwner(owner);
        taskRelaDo.setCycleGap(cycleGap);
        return taskRelaDo;
    }

    public static List<InstanceDisplayDO> createExpectQueryTasksByDate(int i) {
        List<InstanceDisplayDO> instanceDisplayDOList = new ArrayList<InstanceDisplayDO>();
        InstanceDisplayDO instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("8012752014052100");
        instanceDisplayDO.setTaskId(801275);
        instanceDisplayDO.setTaskName("Atom#101262#有当天push送达的主app日安卓uv");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setTriggerTime(1400617800000L);
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setOwner("jiang.zhu");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/atom.101262.8012752014052100.20140521");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setChildren(null);
        instanceDisplayDO.setParents(null);
        instanceDisplayDO.setPrioLvl(3);
        switch (i) {
            case 0: //时间点+taskId
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 1: //时间点+taskIds
                instanceDisplayDOList.add(instanceDisplayDO);
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-21");
                instanceDisplayDO.setTaskStatusId("5009222014052100");
                instanceDisplayDO.setTaskId(500922);
                instanceDisplayDO.setTaskName("Mail#预约预订客服工作日报");
                instanceDisplayDO.setStatus(3);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400634000000L);
                instanceDisplayDO.setDatabaseSrc("gp57");
                instanceDisplayDO.setOwner("gang.lu");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/mail.200.5009222014052100.20140521");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(2);
                instanceDisplayDOList.add(0, instanceDisplayDO);
                break;
            case 2: //时间点+taskName
                instanceDisplayDO.setTaskStatusId("5009222014052100");
                instanceDisplayDO.setTaskId(500922);
                instanceDisplayDO.setTaskName("Mail#预约预订客服工作日报");
                instanceDisplayDO.setTriggerTime(1400634000000L);
                instanceDisplayDO.setDatabaseSrc("gp57");
                instanceDisplayDO.setOwner("gang.lu");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/mail.200.5009222014052100.20140521");
                instanceDisplayDO.setPrioLvl(2);
                instanceDisplayDOList.add(0, instanceDisplayDO);
                break;
            case 3: //时间点+taskNameOrId + status
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 4: //时间点+taskNameOrId + status
                break;
            case 5: //时间点+taskNameOrId + cycle
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 6: //时间点+taskNameOrId + cycle
                break;
            case 7: //时间点+taskNameOrId + owner
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 8: //时间点+taskNameOrId + owner
                break;
            case 9: //时间点+taskNameOrId + prioLvl
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 10: //时间点+taskNameOrId + prioLvl
                break;
            case 11: //时间点 + prioLvl + status
                instanceDisplayDO.setTaskStatusId("6013312014052100");
                instanceDisplayDO.setTaskId(601331);
                instanceDisplayDO.setTaskName("hive##bi.dpdw_union_group_follownote_add");
                instanceDisplayDO.setTriggerTime(1400601900000L);
                instanceDisplayDO.setOwner("zhao.han");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/bi.dpdw_union_group_follownote_add.6013312014052100.20140520");
                instanceDisplayDO.setPrioLvl(3);
                instanceDisplayDO.setStatus(0);
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
        }
        return instanceDisplayDOList;
    }

    public static List<InstanceDisplayDO> createExpectQueryTasksByInterval(int i) {
        List<InstanceDisplayDO> instanceDisplayDOList = new ArrayList<InstanceDisplayDO>();
        InstanceDisplayDO instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("8012752014052100");
        instanceDisplayDO.setTaskId(801275);
        instanceDisplayDO.setTaskName("Atom#101262#有当天push送达的主app日安卓uv");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setTriggerTime(1400617800000L);
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setOwner("jiang.zhu");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/atom.101262.8012752014052100.20140521");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setChildren(null);
        instanceDisplayDO.setParents(null);
        instanceDisplayDO.setPrioLvl(3);
        instanceDisplayDOList.add(instanceDisplayDO);
        instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-22");
        instanceDisplayDO.setTaskStatusId("8012752014052200");
        instanceDisplayDO.setTaskId(801275);
        instanceDisplayDO.setTaskName("Atom#101262#有当天push送达的主app日安卓uv");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setTriggerTime(1400704200000L);
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setOwner("jiang.zhu");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/atom.101262.8012752014052200.20140522");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setChildren(null);
        instanceDisplayDO.setParents(null);
        instanceDisplayDO.setPrioLvl(3);
        instanceDisplayDOList.add(0, instanceDisplayDO);
        switch (i) {
            case 0: //时间段+taskId
                break;
            case 1: //时间段+taskIds
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-21");
                instanceDisplayDO.setTaskStatusId("5009222014052100");
                instanceDisplayDO.setTaskId(500922);
                instanceDisplayDO.setTaskName("Mail#预约预订客服工作日报");
                instanceDisplayDO.setStatus(3);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400634000000L);
                instanceDisplayDO.setDatabaseSrc("gp57");
                instanceDisplayDO.setOwner("gang.lu");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/mail.200.5009222014052100.20140521");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(2);
                instanceDisplayDOList.add(1, instanceDisplayDO);
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-22");
                instanceDisplayDO.setTaskStatusId("5009222014052200");
                instanceDisplayDO.setTaskId(500922);
                instanceDisplayDO.setTaskName("Mail#预约预订客服工作日报");
                instanceDisplayDO.setStatus(3);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400720400000L);
                instanceDisplayDO.setDatabaseSrc("gp57");
                instanceDisplayDO.setOwner("gang.lu");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/mail.200.5009222014052200.20140522");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(2);
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 2: //时间段+taskName
                instanceDisplayDOList.clear();
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-21");
                instanceDisplayDO.setTaskStatusId("5009222014052100");
                instanceDisplayDO.setTaskId(500922);
                instanceDisplayDO.setTaskName("Mail#预约预订客服工作日报");
                instanceDisplayDO.setStatus(3);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400634000000L);
                instanceDisplayDO.setDatabaseSrc("gp57");
                instanceDisplayDO.setOwner("gang.lu");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/mail.200.5009222014052100.20140521");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(2);
                instanceDisplayDOList.add(instanceDisplayDO);
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-22");
                instanceDisplayDO.setTaskStatusId("5009222014052200");
                instanceDisplayDO.setTaskId(500922);
                instanceDisplayDO.setTaskName("Mail#预约预订客服工作日报");
                instanceDisplayDO.setStatus(3);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400720400000L);
                instanceDisplayDO.setDatabaseSrc("gp57");
                instanceDisplayDO.setOwner("gang.lu");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/mail.200.5009222014052200.20140522");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(2);
                instanceDisplayDOList.add(instanceDisplayDO);
                break;
            case 3: //时间段+taskNameOrId + status
                break;
            case 4: //时间段+taskNameOrId + status
                instanceDisplayDOList.clear();
                break;
            case 5: //时间段+taskNameOrId + cycle
                break;
            case 6: //时间段+taskNameOrId + cycle
                instanceDisplayDOList.clear();
                break;
            case 7: //时间段+taskNameOrId + owner
                break;
            case 8: //时间段+taskNameOrId + owner
                instanceDisplayDOList.clear();
                break;
            case 9: //时间段+taskNameOrId + prioLvl
                break;
            case 10: //时间段+taskNameOrId + prioLvl
                instanceDisplayDOList.clear();
                break;
            case 11: //时间段 + prioLvl + status
                instanceDisplayDOList.clear();
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-21");
                instanceDisplayDO.setTaskStatusId("6013312014052100");
                instanceDisplayDO.setTaskId(601331);
                instanceDisplayDO.setTaskName("hive##bi.dpdw_union_group_follownote_add");
                instanceDisplayDO.setStatus(0);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400601900000L);
                instanceDisplayDO.setDatabaseSrc("hive");
                instanceDisplayDO.setOwner("zhao.han");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/bi.dpdw_union_group_follownote_add.6013312014052100.20140520");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(3);
                instanceDisplayDOList.add(instanceDisplayDO);
                instanceDisplayDO = new InstanceDisplayDO();
                instanceDisplayDO.setStartTime(null);
                instanceDisplayDO.setEndTime(null);
                instanceDisplayDO.setTimeId("2014-05-22");
                instanceDisplayDO.setTaskStatusId("6013312014052200");
                instanceDisplayDO.setTaskId(601331);
                instanceDisplayDO.setTaskName("hive##bi.dpdw_union_group_follownote_add");
                instanceDisplayDO.setStatus(0);
                instanceDisplayDO.setCycle("D");
                instanceDisplayDO.setTriggerTime(1400688300000L);
                instanceDisplayDO.setDatabaseSrc("hive");
                instanceDisplayDO.setOwner("zhao.han");
                instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/bi.dpdw_union_group_follownote_add.6013312014052200.20140521");
                instanceDisplayDO.setRecallNum(0);
                instanceDisplayDO.setRunNum(0);
                instanceDisplayDO.setChildren(null);
                instanceDisplayDO.setParents(null);
                instanceDisplayDO.setPrioLvl(3);
                instanceDisplayDOList.add(0, instanceDisplayDO);

                break;
        }
        return instanceDisplayDOList;
    }

    public static InstanceQueryDO createInputInstanceQueryDOByDate(int i) {
        InstanceQueryDO instanceQueryDO = new InstanceQueryDO();
        instanceQueryDO.setStartTime("2014-05-21");
        instanceQueryDO.setEndTime("2014-05-21");
        List<String> ids = new ArrayList<String>();
        ids.add("801275");
        instanceQueryDO.setTaskIds(ids);
        instanceQueryDO.setStatus(100);
        instanceQueryDO.setCycle(null);
        instanceQueryDO.setOwner(null);
        instanceQueryDO.setPrioLvl(100);
        switch (i) {
            case 0: //时间点+taskId
                break;
            case 1: //时间点+taskIds
                ids.add("500922");
                instanceQueryDO.setTaskIds(ids);
                break;
            case 2: //时间点+taskName
                instanceQueryDO.setTaskIds(null);
                instanceQueryDO.setTaskName("Mail#预约预订客服");
                break;
            case 3: //时间点+taskId + status
                instanceQueryDO.setStatus(3);
                break;
            case 4: //时间点+taskId + status
                instanceQueryDO.setStatus(-1);
                break;
            case 5: //时间点+taskId + cycle
                instanceQueryDO.setCycle("D");
                break;
            case 6: //时间点+taskId + cycle
                instanceQueryDO.setCycle("H");
                break;
            case 7: //时间点+taskId + owner
                instanceQueryDO.setOwner("jiang.zhu");
                break;
            case 8: //时间点+taskId + owner
                instanceQueryDO.setOwner("shanshan.jin");
                break;
            case 9: //时间点+taskId + prioLvl
                instanceQueryDO.setPrioLvl(3);
                break;
            case 10: //时间点+taskId + prioLvl
                instanceQueryDO.setPrioLvl(0);
                break;
            case 11: //时间点 + prioLvl + status
                instanceQueryDO.setPrioLvl(3);
                instanceQueryDO.setTaskName(null);
                instanceQueryDO.setTaskIds(null);
                instanceQueryDO.setStatus(0);
                break;
        }
        return instanceQueryDO;
    }

    public static InstanceQueryDO createInputInstanceQueryDOByInterval(int i) {
        InstanceQueryDO instanceQueryDO = new InstanceQueryDO();
        instanceQueryDO.setStartTime("2014-05-21");
        instanceQueryDO.setEndTime("2014-05-22");
        List<String> ids = new ArrayList<String>();
        ids.add("801275");
        instanceQueryDO.setTaskIds(ids);
        instanceQueryDO.setStatus(100);
        instanceQueryDO.setCycle(null);
        instanceQueryDO.setOwner(null);
        instanceQueryDO.setPrioLvl(100);
        switch (i) {
            case 0: //时间段+taskId
                break;
            case 1: //时间段+taskIds
                ids.add("500922");
                instanceQueryDO.setTaskIds(ids);
                break;
            case 2: //时间段+taskName
                instanceQueryDO.setTaskIds(null);
                instanceQueryDO.setTaskName("Mail#预约预订客服");
                break;
            case 3: //时间段+taskId + status
                instanceQueryDO.setStatus(3);
                break;
            case 4: //时间段+taskId + status
                instanceQueryDO.setStatus(-1);
                break;
            case 5: //时间段+taskId + cycle
                instanceQueryDO.setCycle("D");
                break;
            case 6: //时间段+taskId + cycle
                instanceQueryDO.setCycle("H");
                break;
            case 7: //时间段+taskId + owner
                instanceQueryDO.setOwner("jiang.zhu");
                break;
            case 8: //时间段+taskId + owner
                instanceQueryDO.setOwner("shanshan.jin");
                break;
            case 9: //时间段+taskId + prioLvl
                instanceQueryDO.setPrioLvl(3);
                break;
            case 10: //时间段+taskId + prioLvl
                instanceQueryDO.setPrioLvl(0);
                break;
            case 11: //时间段 + prioLvl + status
                instanceQueryDO.setPrioLvl(3);
                instanceQueryDO.setTaskName(null);
                instanceQueryDO.setTaskIds(null);
                instanceQueryDO.setStatus(0);
                break;
        }
        return instanceQueryDO;
    }

    public static InstanceDisplayDO createExpectGetTaskStatus() {
        InstanceDisplayDO instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("8012752014052100");
        instanceDisplayDO.setTaskId(801275);
        instanceDisplayDO.setTaskName("Atom#101262#有当天push送达的主app日安卓uv");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setTriggerTime(1400617800000L);
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setOwner("jiang.zhu");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/atom.101262.8012752014052100.20140521");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setPrioLvl(3);
        instanceDisplayDO.setChildren(new ArrayList<String>());
        instanceDisplayDO.setParents(new ArrayList<String>());
        return instanceDisplayDO;
    }

    public static List<InstanceDisplayDO> createExpectQueryDirectRelation() {
        List<InstanceDisplayDO> instanceDisplayDOList = new ArrayList<InstanceDisplayDO>();
        InstanceDisplayDO instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("8012752014052100");
        instanceDisplayDO.setTaskId(801275);
        instanceDisplayDO.setTaskName("Atom#101262#有当天push送达的主app日安卓uv");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setTriggerTime(1400617800000L);
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setOwner("jiang.zhu");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/atom.101262.8012752014052100.20140521");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setPrioLvl(3);
        List<String> list = new ArrayList<String>();
        list.add("2000712014052100");
        list.add("6009512014052100");
        instanceDisplayDO.setChildren(new ArrayList<String>());
        instanceDisplayDO.setParents(list);
        instanceDisplayDOList.add(instanceDisplayDO);

        instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("6009512014052100");
        instanceDisplayDO.setTaskId(600951);
        instanceDisplayDO.setTaskName("hive##bi.dpdw_op_push_base");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setTriggerTime(1400601900000L);
        instanceDisplayDO.setOwner("xiaoning.yue");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/bi.dpdw_op_push_base.6009512014052100.20140520");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setPrioLvl(2);
        list = new ArrayList<String>();
        list.add("8012752014052100");
        instanceDisplayDO.setChildren(list);
        instanceDisplayDO.setParents(null);
        instanceDisplayDOList.add(instanceDisplayDO);

        instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("2000712014052100");
        instanceDisplayDO.setTaskId(200071);
        instanceDisplayDO.setTaskName("hive##bi.dpmid_mb_uv_sd");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setTriggerTime(1400605200000L);
        instanceDisplayDO.setOwner("ryan.zhang");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/bi.dpmid_mb_uv_sd.2000712014052100.20140520");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setPrioLvl(1);
        list = new ArrayList<String>();
        list.add("8012752014052100");
        instanceDisplayDO.setChildren(list);
        instanceDisplayDO.setParents(null);
        instanceDisplayDOList.add(instanceDisplayDO);

        return instanceDisplayDOList;
    }

    public static List<InstanceDisplayDO> createExpectQueryAllRelation() {
        List<InstanceDisplayDO> instanceDisplayDOList = new ArrayList<InstanceDisplayDO>();
        InstanceDisplayDO instanceDisplayDO = new InstanceDisplayDO();
        instanceDisplayDO.setStartTime(null);
        instanceDisplayDO.setEndTime(null);
        instanceDisplayDO.setTimeId("2014-05-21");
        instanceDisplayDO.setTaskStatusId("8012752014052100");
        instanceDisplayDO.setTaskId(801275);
        instanceDisplayDO.setTaskName("Atom#101262#有当天push送达的主app日安卓uv");
        instanceDisplayDO.setStatus(3);
        instanceDisplayDO.setCycle("D");
        instanceDisplayDO.setTriggerTime(1400617800000L);
        instanceDisplayDO.setDatabaseSrc("hive");
        instanceDisplayDO.setOwner("jiang.zhu");
        instanceDisplayDO.setLogPath("/data/deploy/dwarch/log/calculate/atom.101262.8012752014052100.20140521");
        instanceDisplayDO.setRecallNum(0);
        instanceDisplayDO.setRunNum(0);
        instanceDisplayDO.setPrioLvl(3);
        instanceDisplayDO.setChildren(new ArrayList<String>());
        instanceDisplayDO.setParents(new ArrayList<String>());
        instanceDisplayDOList.add(instanceDisplayDO);
        return instanceDisplayDOList;
    }

    public static List<String> createExpectSonTaskStatusIdListByTaskStatusIdList() {
        List<String> taskStatusIdList = new ArrayList<String>();
        taskStatusIdList.add("2009712014052100");
        taskStatusIdList.add("2018912014052100");
        taskStatusIdList.add("2022812014052100");
        taskStatusIdList.add("2023512014052100");
        taskStatusIdList.add("2047112014052100");
        taskStatusIdList.add("2053612014052100");
        taskStatusIdList.add("3003612014052100");
        taskStatusIdList.add("4001912014052100");
        taskStatusIdList.add("2002612014052100");
        taskStatusIdList.add("2003012014052100");
        taskStatusIdList.add("4001212014052100");
        taskStatusIdList.add("8012752014052100");
        taskStatusIdList.add("8012762014052100");
        taskStatusIdList.add("8012772014052100");
        taskStatusIdList.add("8012782014052100");
        return taskStatusIdList;
    }

    public static List<Map<String, Object>> createExpectMapList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2009712014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2018912014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2022812014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2023512014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2047112014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2053612014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "3003612014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "4001912014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2002612014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "2003012014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "4001212014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "8012752014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "8012762014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "8012772014052100");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("pre_sts_id", "2000712014052100");
        map.put("task_status_id", "8012782014052100");
        list.add(map);
        return list;
    }

    public static TaskSQLParserDO createExpectTaskSQLParserDO(int i) {
        TaskSQLParserDO taskSQLParserDO = new TaskSQLParserDO();
        List<String> parentTableNames = new ArrayList<String>();
        parentTableNames.add("dpdm_user_shop_base");
        parentTableNames.add("bi.dpods_dp_shopregion");
        parentTableNames.add("bi.dpods_dp_regionlist");
        taskSQLParserDO.setParentTableNames(parentTableNames);
        List<String> childTableNames = new ArrayList<String>();
        childTableNames.add("dpmid_sunny_test");
        taskSQLParserDO.setChildTableNames(childTableNames);
        List<TaskRelaDO> parentTaskRelaDOs = new ArrayList<TaskRelaDO>();
        TaskRelaDO taskRelaDO = new TaskRelaDO();
        taskRelaDO.setCycleGap("D0");
        taskRelaDO.setTaskPreId(10263);
        taskRelaDO.setTaskName("mysql2hdfs##DP_ShopRegion");
        taskRelaDO.setOwner("ryan.zhang");
        parentTaskRelaDOs.add(taskRelaDO);
        taskRelaDO = new TaskRelaDO();
        taskRelaDO.setCycleGap("D0");
        taskRelaDO.setTaskPreId(10302);
        taskRelaDO.setTaskName("mysql2hdfs##DP_RegionList");
        taskRelaDO.setOwner("ryan.zhang");
        parentTaskRelaDOs.add(taskRelaDO);
        taskRelaDO = new TaskRelaDO();
        taskRelaDO.setCycleGap("D0");
        taskRelaDO.setTaskPreId(200711);
        taskRelaDO.setTaskName("hive##bi.user_profile_shop");
        taskRelaDO.setOwner("ben.lin");
        parentTaskRelaDOs.add(taskRelaDO);
        taskSQLParserDO.setParentTaskRelaDOs(parentTaskRelaDOs);
        if(i == 1)
            taskSQLParserDO.setLaunch(1);
        return taskSQLParserDO;
    }
}
