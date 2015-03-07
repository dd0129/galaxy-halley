package com.dianping.data.warehouse.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskUtilsTest {

    @org.junit.Test
    public void testGenerateInstanceID() throws Exception {
        System.out.println(TaskUtils.generateInstanceID(10001, "D", new Date()));
        System.out.println(TaskUtils.generateRelaInstanceID(10001, System.currentTimeMillis(),"W0"));
    }

    @Test
    public void testGenerateRelaInstanceID() throws Exception {

    }
}