package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongdi.tang on 14-6-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class PublishLogDAOTest {

    @Resource
    private PublishFileDAO publishLogDAO;

    @Test
    public void testGetPublishLogDOByID() throws Exception {
//        PublishLogDO log = publishLogDAO.getPublishLogDOByID("123");
//        System.out.println(log);
    }

    @Test
    public void testInsertPublishLogDO() throws Exception {
        List<PublishFileDO> list = publishLogDAO.getPublishListByID("123");
        System.out.println(list);
    }
}
