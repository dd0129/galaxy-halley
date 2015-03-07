package com.dianping.data.warehouse.halley.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sunny on 14-8-13.
 */
public class CommonUtils {

    public static Date strToDate(String strDate, String strFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        Date date = format.parse(strDate);
        return date;
    }

    public static int getDayDiff(Date date0, Date date1) {
        final int DAY_TO_MS = 1000 * 3600 * 24;
        int diff = (int) Math.floor(date0.getTime() - date1.getTime()) / DAY_TO_MS;
        return diff;
    }

    public static String getCurrentTimeStampStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTimeStamp = simpleDateFormat.format(new Date());
        return nowTimeStamp;
    }

    public static String getCurrentDateStr() {
        Calendar cal = Calendar.getInstance();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return date;
    }
}
