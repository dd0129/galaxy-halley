package com.dianping.data.warehouse.core.util;

import org.junit.Test;

public class HalleyStrUtilsTest {

    @Test
    public void testConcatPathStr() throws Exception {
        String s = HalleyStrUtils.concatPathStr("\\efsae", "/saoefhisef", "\\saehfosaef", "aosefha");
        System.out.println(s);

    }
}