package com.dianping.data.warehouse.worker.jgit;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-6-19.
 */
public class JGitUtilsTest {
    @Test
    public void testPull() throws Exception {
        boolean flag1 = JGitUtils.pull("git@code.dianpingoa.com:datadp/data_analysis.git", "data_analysis");
        boolean flag2 = JGitUtils.pull("git@code.dianpingoa.com:warehouse.git","warehouse");
        Assert.assertTrue(flag1);
        Assert.assertTrue(flag2);
    }
}
