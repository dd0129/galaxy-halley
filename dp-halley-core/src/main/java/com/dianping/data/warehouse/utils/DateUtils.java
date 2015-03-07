package com.dianping.data.warehouse.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Deprecated
public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static String getAppointDay(String time_id, String type, int gap) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = sdf.parse(time_id);
        c.setTime(date);
        if (type.equals("D")) {
            c.add(Calendar.DAY_OF_MONTH, -gap);
            return sdf.format(c.getTime());
        } else if (type.equals("M")) {
            c.add(Calendar.MONDAY, -gap);
            return sdf.format(c.getTime());
        } else if (type.equals("H")) {
            c.add(Calendar.HOUR, -gap);
            return sdf.format(c.getTime());
        } else {
            return null;
        }
    }

    public static String getDay8() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    public static String getLastMonth7() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return sdf.format(c.getTime());
    }

    public static String getDay10(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(d);
    }

    public static String getDay8(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        return sdf.format(d);
    }

    public static String getLastDay10(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd");
            return sdf.format(new Date(d.getTime() - 86400 * 1000));
        } catch (Exception e) {
            logger.error("get last day error", e);
            return null;
        }

    }

    private static String getFirstDayLastMonth10(String time_id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = sdf.parse(time_id);
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getLastDayLastMonth10(String time_id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Date date = sdf.parse(time_id);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String get_cal_dt(String time_id, String offset_type, String offset) {
        if (offset_type.equals("offset")) {
            String type2 = offset.substring(0, 1);
            StringUtils.substring(offset, 1);
            int gap = Integer.valueOf(StringUtils.substring(offset, 1));
            if (gap >= 100) {
                logger.error(gap + " is illegal for interval more than 1000");
                return null;
            }
            try {
                return DateUtils.getAppointDay(time_id, type2, gap);
            } catch (Exception e) {
                logger.error("get appoint day error", e);
                return null;
            }
        } else {
            logger.error(offset + " is illegal");
            return null;
        }


    }

    public static String getNCal_dt(String cal_dt, String pattern) {
        if (pattern.equals("${ncal_dt}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    public static String getEndDayThisMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${end_day_this_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DAY_OF_MONTH, -1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getEndDayThisMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${end_day_this_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DAY_OF_MONTH, -1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getLastWeek8(String cal_dt, String pattern) {
        if (pattern.equals("${last_week8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, -6);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getLastWeek10(String cal_dt, String pattern) {
        if (pattern.equals("${last_week10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, -6);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayThisMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_this_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getCal_dt8(String cal_dt, String pattern) {
        if (pattern.equals("${cal_dt8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getNCal_dt8(String cal_dt, String pattern) {
        if (pattern.equals("${ncal_dt8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayThisMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_this_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayLastMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_last_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayLastMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_last_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getNdays_cal_dt(String cal_dt, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date date = sdf.parse(cal_dt);
            c.setTime(date);
            String s = pattern.replace("${", "").replace("days_cal_dt}", "");
            Integer interval = new Integer(pattern.replace("${", "").replace("days_cal_dt}", ""));
            c.add(Calendar.DAY_OF_MONTH, -interval);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLastDayLastMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${last_day_last_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DATE, -1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getLastDayLastMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${last_day_last_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DATE, -1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getThisHour(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getMonNextWeek8(String cal_dt, String pattern) {
        if (pattern.equals("${monday_next_week8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getMonNextWeek10(String cal_dt, String pattern) {
        if (pattern.equals("${monday_next_week10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static long transferDateStr(String dateStr) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        return date.getTime();
    }

    public static String getReplaceCal(String para, String offset_type, String offset, Date init_date) {
        try {
            String cal_dt = DateUtils.get_cal_dt(DateUtils.getLastDay10(init_date), offset_type, offset);
            if (StringUtils.isBlank(para)) {
                return para;
            }
            String ncal_dt = DateUtils.getNCal_dt(cal_dt, "${ncal_dt}");
            String cal_dt8 = DateUtils.getCal_dt8(cal_dt, "${cal_dt8}");
            String ncal_dt8 = DateUtils.getNCal_dt8(cal_dt, "${ncal_dt8}");

            String last_week8 = DateUtils.getLastWeek8(cal_dt, "${last_week8}");
            String last_week10 = DateUtils.getLastWeek10(cal_dt, "${last_week10}");

            String end_day_last_month8 = DateUtils.getEndDayThisMonth8(cal_dt, "${end_day_this_month8}");
            String end_day_last_month10 = DateUtils.getEndDayThisMonth10(cal_dt, "${end_day_this_month10}");

            String mon_next_week8 = DateUtils.getMonNextWeek8(cal_dt, "${monday_next_week8}");
            String mon_next_week10 = DateUtils.getMonNextWeek10(cal_dt, "${monday_next_week10}");

            String first_day_this_month8 = DateUtils.getFirstDayThisMonth8(cal_dt, "${first_day_this_month8}");
            String first_day_this_month10 = DateUtils.getFirstDayThisMonth10(cal_dt, "${first_day_this_month10}");

            String first_day_last_month8 = DateUtils.getFirstDayLastMonth8(cal_dt, "${first_day_last_month8}");
            String first_day_last_month10 = DateUtils.getFirstDayLastMonth10(cal_dt, "${first_day_last_month10}");

            String last_day_last_month8 = DateUtils.getLastDayLastMonth8(cal_dt, "${last_day_last_month8}");
            String last_day_last_month10 = DateUtils.getLastDayLastMonth10(cal_dt, "${last_day_last_month10}");

            String this_hour = DateUtils.getThisHour(init_date);

            String Ndays_cal_dt = DateUtils.getNdays_cal_dt(cal_dt, "${30days_cal_dt}");

            return para.replace("${cal_dt}", cal_dt)
                    .replace("${ncal_dt}", ncal_dt)
                    .replace("${cal_dt8}", cal_dt8)
                    .replace("${ncal_dt8}", ncal_dt8)
                    .replace("${last_week8}", last_week8)
                    .replace("${last_week10}", last_week10)
                    .replace("${monday_next_week8}", mon_next_week8)
                    .replace("${monday_next_week10}", mon_next_week10)
                    .replace("${end_day_this_month8}", end_day_last_month8)
                    .replace("${end_day_this_month10}", end_day_last_month10)
                    .replace("${first_day_this_month8}", first_day_this_month8)
                    .replace("${first_day_this_month10}", first_day_this_month10)
                    .replace("${first_day_last_month8}", first_day_last_month8)
                    .replace("${first_day_last_month10}", first_day_last_month10)
                    .replace("${last_day_last_month8}", last_day_last_month8)
                    .replace("${last_day_last_month10}", last_day_last_month10)
                    .replace("${this_hour}", this_hour)
                    .replace("${30days_cal_dt}", Ndays_cal_dt);
        } catch (Exception e) {
            logger.error("date variable is illegal", e);
            return null;
        }


    }


    public static String generateRelaInstanceID(Integer pre_id, Long fire_time, String gap) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fire_time);
        Date date = calendar.getTime();

        String type = gap.substring(0, 1);
        int interval = new Integer(gap.substring(1));

        if (type.equals("H")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, -1 * interval);

            String pre_str_date = sdf.format(cal.getTime());

            return pre_id + pre_str_date;
        } else if (type.equals("D")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1 * interval);

            String pre_str_date = sdf.format(cal.getTime());
            return pre_id + pre_str_date;
        } else if (type.equals("M")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM0100");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1 * interval);

            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else if (type.equals("W")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMWW00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.WEEK_OF_YEAR, -1 * interval);
            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else {
            logger.error("error input cycle gap " + gap);
            return null;
        }
    }

}
