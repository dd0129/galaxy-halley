package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.TaskTestUtils;
import com.dianping.data.warehouse.core.lion.LionUtil;
import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import com.dianping.data.warehouse.halley.domain.TaskSQLParserDO;
import com.dianping.data.warehouse.halley.service.TaskPublish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by hongdi.tang on 14-6-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class TaskPublishImplTest {

    @Resource(name = "taskPublishImpl")
    private TaskPublish taskPublish;

    @Test
    public void testPublish() throws Exception {
       // service.publish("warehouse","warehouse/dol/algo.dpdm_user_region.dol");
        TaskSQLParserDO para2 = taskPublish.publish("warehouse","warehouse/dol/algo.dpdm_user_region.dol");
        System.out.println(para2);
        TaskSQLParserDO output = taskPublish.publish("warehouse", "/warehouse/dol/dwdev.product.dpmid_sunny_test.dol");
        TaskSQLParserDO expect = TaskTestUtils.createExpectTaskSQLParserDO(0);
        //assertEquals(expect, output);

    }


//    @Test
//    public void testSqlParse() throws Exception {
//        TaskSQLParserDO output = taskPublish.sqlParse(205401);
//        TaskSQLParserDO expect = TaskTestUtils.createExpectTaskSQLParserDO(1);
//        assertEquals(expect, output);
//        output = taskPublish.sqlParse(20540132);
//        assertEquals(null, output);
//    }

    @Test
    public void testRollback() throws Exception {

    }
    @Test
    public void testValidateHost(){
        TaskPublishImpl service = new TaskPublishImpl();
        List<PublishFileDO> list = new ArrayList<PublishFileDO>();
        PublishFileDO file = new PublishFileDO();
        file.setFlag(true);
        file.setPublishId("123");
        file.setHost("12");
        PublishFileDO file1 = new PublishFileDO();
        file1.setFlag(true);
        file1.setPublishId("123");
        file1.setHost("13");
        list.add(file);
        list.add(file1);
        String[] hosts = LionUtil.getProperty("galaxy-halley.worker_hosts").split(";");
        boolean flag = service.validateHost(list,hosts);
        System.out.println(flag);
    }
}
