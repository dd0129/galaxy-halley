package com.dianping.data.warehouse.common;

import com.dianping.data.warehouse.domain.ExternalDO;
import com.dianping.data.warehouse.domain.InstanceDO;

/**
 * Created by adima on 14-3-29.
 */
public class MockData {
    public static ExternalDO getExtInstance(){
        ExternalDO extDO = new ExternalDO();
        extDO.setImplClass("com.dianping.data.warehouse.external.SuccessClientTestImpl");
        extDO.setOwner("hongdi.tang");
        return extDO;
    }
    public static InstanceDO genInstance(){
        InstanceDO inst = new InstanceDO();
        inst.setInstanceId("8004532014082600");
        inst.setTaskId(10001);
        inst.setTaskGroupId(1);
        inst.setTaskName("test");
        inst.setTaskObj("e:/data/test.bat");
        inst.setRunningPrio(100);
        inst.setPara1("-t xx");
        inst.setPara2("-t xx");
        inst.setPara3("-t xx");
        inst.setLogPath("d:/data/test.log");
        inst.setCycle("D");
        inst.setTimeId("2014-03-29");
        inst.setStatus(-1);
        inst.setPrioLvl(3);
        inst.setRunNum(0);
        inst.setType(1);
        inst.setDatabaseSrc("mysql_DianPingDW");
        inst.setTableName("test");
        inst.setFreq("10 5 0 * * ?");
        inst.setCalDt("2014-03-28");
        inst.setIfPre(-1);
        inst.setStsDesc("test only");
        inst.setRecallNum(0);
        inst.setOwner("hongdi.tang");
        inst.setTriggerTime(System.currentTimeMillis());
        inst.setRecallCode("213");
        inst.setSuccessCode("0");
        inst.setIfWait(-1);
        inst.setIfRecall(-1);
        inst.setWaitCode("123");
        inst.setTimeout(90);
        inst.setRecallLimit(10);
        inst.setRecallInterval(10);
        inst.setInQueueTimeMillis(System.currentTimeMillis());
        inst.setInstRelaList(null);
        inst.setStartTime(null);
        inst.setEndTime(null);
        inst.setTimestamp(null);
        inst.setJobCode(-1);
        return inst;
    }
}
