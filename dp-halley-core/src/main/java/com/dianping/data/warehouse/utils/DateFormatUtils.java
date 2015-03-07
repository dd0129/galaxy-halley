package com.dianping.data.warehouse.utils;

import java.text.SimpleDateFormat;

/**
 * Created by hongdi.tang on 14-5-26.
 */
public class DateFormatUtils {
    private DateFormatUtils(){}
    private static SimpleDateFormat formatter;

    private synchronized static SimpleDateFormat init(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat getFormatter(){
        if(formatter == null){
            formatter = init();
        }
        return formatter;
    }
}
