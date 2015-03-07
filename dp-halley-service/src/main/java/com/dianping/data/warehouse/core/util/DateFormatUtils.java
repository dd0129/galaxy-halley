package com.dianping.data.warehouse.core.util;

import java.text.SimpleDateFormat;

public class DateFormatUtils {
    private DateFormatUtils() {
    }

    private static SimpleDateFormat formatter;

    private synchronized static SimpleDateFormat init() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat getFormatter() {
        if (formatter == null) {
            formatter = init();
        }
        return formatter;
    }
}