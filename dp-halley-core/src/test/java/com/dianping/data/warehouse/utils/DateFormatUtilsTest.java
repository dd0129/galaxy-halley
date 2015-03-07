package com.dianping.data.warehouse.utils;

import java.util.Date;

/**
 * Created by hongdi.tang on 14-5-26.
 */
public class DateFormatUtilsTest {
    @org.junit.Test
    public void testGetFormatter() throws Exception {
        System.out.println(DateFormatUtils.getFormatter().format(new Date()));
    }
}
