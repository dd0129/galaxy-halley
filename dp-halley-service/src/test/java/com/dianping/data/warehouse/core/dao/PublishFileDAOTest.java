package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongdi.tang on 14-7-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class PublishFileDAOTest {
    @Resource(name="publishFileDAO")
    private PublishFileDAO dao;
    @Test
    public void testGetPublishListByID() throws Exception {
        List<PublishFileDO> list = dao.getPublishListByID("publishID_20140709103229968_895");
        System.out.println(list.size());
        System.out.println(new String[]{"32423","23423423","234234"}.length);
    }

    @Test
    public void testInsertPublishFile() throws Exception {

    }
}
