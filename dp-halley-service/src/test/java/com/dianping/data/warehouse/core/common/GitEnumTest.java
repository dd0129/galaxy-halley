package com.dianping.data.warehouse.core.common;

import com.dianping.data.warehouse.core.jgit.JGitUtils;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-7-8.
 */
public class GitEnumTest {
    @Test
    public void testGetGitPath() throws Exception {

    }

    @Test
    public void testGetMappingBaseDir() throws Exception {
        System.out.println(JGitUtils.getGitPath("warehouse"));
        System.out.println(JGitUtils.getGitPath("data_analysis"));
        System.out.println(JGitUtils.getWorkerBaseDir("data_analysis"));
        System.out.println(JGitUtils.getWorkerBaseDir("data_analysis"));
    }
}
