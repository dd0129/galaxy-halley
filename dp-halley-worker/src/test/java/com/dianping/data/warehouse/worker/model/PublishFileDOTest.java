package com.dianping.data.warehouse.worker.model;


import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-6-18.
 */
public class PublishFileDOTest {
    @Test
    public void testCreateInstance(){
        PublishFileDO file = new PublishFileDO("d:/test","test.dol");
        System.out.println(file);
        Assert.assertNotNull(file.getPublishId());
    }
}
