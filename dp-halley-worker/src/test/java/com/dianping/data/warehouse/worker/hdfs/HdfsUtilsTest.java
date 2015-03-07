package com.dianping.data.warehouse.worker.hdfs;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-6-20.
 */
public class HdfsUtilsTest {
    @Test
    public void testDownload() throws Exception {
        boolean flag = HdfsUtils.upload("d:/555.xml", "/tmp/55.xml");
        Assert.assertTrue(flag);
    }

    @Test
    public void testUpload() throws Exception {
        boolean flag = HdfsUtils.download("/tmp/55.xml","e:/55.xml");
        Assert.assertTrue(flag);
    }
}
