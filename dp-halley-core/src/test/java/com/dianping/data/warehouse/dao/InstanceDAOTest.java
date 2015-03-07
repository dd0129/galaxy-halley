package com.dianping.data.warehouse.dao;

import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.client.Const;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by adima on 14-3-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext-resource.xml")
public class InstanceDAOTest {

    private static Logger logger = LoggerFactory.getLogger(InstanceDAOTest.class);

    @Resource
    private InstanceDAO dao;

    @Test
    public void testSaveInstance() throws Exception {

    }

    @Test
    public void testSaveInstanceRela() throws Exception {

    }

    @Test
    public void testGetInstanceInfo() throws Exception {
        String s = "1000120120509";
        InstanceDO inst = dao.getInstanceInfo(s);
        Assert.assertEquals(s, inst.getInstanceId());
    }

    @Test
    public void testGetReadyTaskList() throws Exception {
        List<InstanceDO> list = dao.getInitInstanceList(Const.JOB_STATUS.JOB_INIT.getValue(), 1556723123242L);
        System.out.println(list.size());
        Assert.assertNotNull(list);
    }

    @Test
    public void testUpdateTaskReay() throws Exception {
        this.dao.updateInstnaceStatus("1000120120514", Const.JOB_STATUS.JOB_READY.getValue(), Const.JOB_STATUS.JOB_READY.getDesc());
    }

    @Test
    public void testUpdateEndStatus() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = formatter.format(new Date());
        this.dao.updateInstEndStatus("1000120120514", Const.JOB_STATUS.JOB_SUCCESS.getValue(),Const.JOB_STATUS.JOB_READY.getDesc(),currTime,2,null);
    }

    @Test
    public void testUpdateInstanceRunning() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = formatter.format(new Date());
        this.dao.updateInstnaceRunning("1000120120514", Const.JOB_STATUS.JOB_RUNNING.getValue(), Const.JOB_STATUS.JOB_RUNNING.getDesc(), currTime);
    }

    @Test
    public void testGetRelaInstanceList() throws Exception {
        List<InstanceDO> list = this.dao.getRelaInstanceList("5001022013082100");
        logger.info(String.valueOf(list.size()));
        Assert.assertNotNull(list.size());
    }

    @Test
    public void testGetReadyInstanceList() throws Exception {
        List<InstanceDO> list = this.dao.getReadyInstanceList(Const.JOB_STATUS.JOB_READY.getValue());
        logger.info(String.valueOf(list.size()));
        Assert.assertNotNull(list.size());
    }

    @Test
    public void testUpdateInstnaceListStatus() throws Exception {
        Integer rtn = this.dao.updateInstnaceListStatus
                (Const.JOB_STATUS.JOB_INIT.getValue(), Const.JOB_STATUS.JOB_INIT.getDesc(),
                        Const.JOB_STATUS.JOB_WAIT.getValue());
        System.out.println(rtn);
        Assert.assertNotNull(rtn);
    }


}
