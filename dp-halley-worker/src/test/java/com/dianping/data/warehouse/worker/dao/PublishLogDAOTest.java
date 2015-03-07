package com.dianping.data.warehouse.worker.dao;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-6-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class PublishLogDAOTest {

    @Resource
    private PublishLogDAO publishLogDAO;

    @Test
    public void testGetPublishLogDOByID() throws Exception {
//        PublishLogDO log = publishLogDAO.getPublishLogDOByID("123");
//        System.out.println(log);
    }

    @Test
    public void testInsertPublishLogDO() throws Exception {
        PublishFileDO log = new PublishFileDO("22","234");
        publishLogDAO.insertPublishFile(log);
    }
}
