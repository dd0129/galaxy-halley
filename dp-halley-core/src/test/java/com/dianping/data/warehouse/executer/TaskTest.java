package com.dianping.data.warehouse.executer;

import com.dianping.data.warehouse.common.MockData;
import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.domain.TaskReturnDO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by adima on 14-4-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext-resource.xml")
public class TaskTest {
    @Autowired
    private InstanceDAO instDAO;

    InstanceDO inst = null;
    Task2 task = null;

    @Before
    public void setup() throws Exception{
        inst = MockData.genInstance();
        inst.setInstanceId("107292014051400");
        inst.setType(2);
        inst.setTaskObj("d:/data/55.bat");
        inst.setLogPath("e:\\data\\test.log");
        task = new Task2(instDAO,inst,null);
    }
    @Test
    public void testRun() throws Exception {
        task.run();
    }

    @Test
    public void testRecoredExternalLog(){
        TaskReturnDO rtn = new TaskReturnDO();
        rtn.setCode(301);
        rtn.setMessage("test111");
        task.recoredExternalLog(inst, rtn);
        Assert.assertEquals(rtn.getMessage(),"test111");
        rtn.setCode(300);
        rtn.setMessage("success");
        task.recoredExternalLog(inst, rtn);
        Assert.assertEquals(rtn.getMessage(),"test111");
    }
}
