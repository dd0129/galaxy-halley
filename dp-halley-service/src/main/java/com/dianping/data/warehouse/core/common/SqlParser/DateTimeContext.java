package com.dianping.data.warehouse.core.common.SqlParser;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeContext {
    private String DEFAULT_FORMATTER = "yyyy-MM-dd";

    public String plusDays(String curr, int days) {
        return plusDays(curr, days, DEFAULT_FORMATTER);

    }

    public String plusDays(String curr, int days, String formatter) {
        DateTime dt = parseDate(curr);
        return plusDays(dt, days, formatter);

    }

    private String plusDays(DateTime dt, int days, String formatter) {
        return dt.plusDays(days).toString(formatter);
    }

    public String minusDays(String curr, int days) {
        return minusDays(curr, days, DEFAULT_FORMATTER);

    }

    public String minusDays(String curr, int days, String formatter) {
        DateTime dt = parseDate(curr);
        return minusDays(dt, days, formatter);

    }

    private String minusDays(DateTime dt, int days, String formatter) {
        return dt.minusDays(days).toString(formatter);
    }

    public String plusMonths(String curr, int months) {
        return plusMonths(curr, months, DEFAULT_FORMATTER);

    }

    public String plusMonths(String curr, int months, String formatter) {
        DateTime dt = parseDate(curr);
        return plusMonths(dt, months, formatter);

    }

    private String plusMonths(DateTime dt, int months, String formatter) {
        return dt.plusMonths(months).toString(formatter);
    }

    public String minusMonths(String curr, int months) {
        return minusMonths(curr, months, DEFAULT_FORMATTER);

    }

    public String minusMonths(String curr, int months, String formatter) {
        DateTime dt = parseDate(curr);
        return minusMonths(dt, months, formatter);

    }

    private String minusMonths(DateTime dt, int months, String formatter) {
        return dt.minusMonths(months).toString(formatter);
    }

    public String plusWeeks(String curr, int weeks) {
        return plusWeeks(curr, weeks, DEFAULT_FORMATTER);

    }

    public String plusWeeks(String curr, int weeks, String formatter) {
        DateTime dt = parseDate(curr);
        return plusWeeks(dt, weeks, formatter);

    }

    private String plusWeeks(DateTime dt, int weeks, String formatter) {
        return dt.plusWeeks(weeks).toString(formatter);
    }

    public String minusWeeks(String curr, int weeks) {
        return minusWeeks(curr, weeks, DEFAULT_FORMATTER);

    }

    public String minusWeeks(String curr, int weeks, String formatter) {
        DateTime dt = parseDate(curr);
        return minusWeeks(dt, weeks, formatter);

    }

    private String minusWeeks(DateTime dt, int weeks, String formatter) {
        return dt.minusWeeks(weeks).toString(formatter);
    }

    private DateTime parseDate(String strDate) {
        if (strDate == null)
            return new DateTime();
        String cleanStr = strDate.replaceAll("[^0-9]", "");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
        return DateTime.parse(cleanStr, fmt);
    }

    public String getLastDayOfMonth(String curr) {
        DateTime dt = parseDate(curr);
        return getLastDayOfMonth(dt, DEFAULT_FORMATTER);

    }

    public String getLastDayOfMonth(String curr, String formatter) {
        DateTime dt = parseDate(curr);
        return getLastDayOfMonth(dt, formatter);

    }

    private String getLastDayOfMonth(DateTime dt, String formatter) {
        return dt.dayOfMonth().withMaximumValue().toString(formatter);

    }

    public String getFirstDayOfMonth(String curr) {
        DateTime dt = parseDate(curr);
        return getFirstDayOfMonth(dt, DEFAULT_FORMATTER);

    }

    public String getFirstDayOfMonth(String curr, String formatter) {
        DateTime dt = parseDate(curr);
        return getFirstDayOfMonth(dt, formatter);

    }

    private String getFirstDayOfMonth(DateTime dt, String formatter) {
        return dt.dayOfMonth().withMinimumValue().toString(formatter);

    }

    public String getLastDayOfWeek(String curr) {
        DateTime dt = parseDate(curr);
        return getLastDayOfWeek(dt, DEFAULT_FORMATTER);

    }

    public String getLastDayOfWeek(String curr, String formatter) {
        DateTime dt = parseDate(curr);
        return getLastDayOfWeek(dt, formatter);

    }

    private String getLastDayOfWeek(DateTime dt, String formatter) {
        return dt.dayOfWeek().withMaximumValue().toString(formatter);

    }

    public String getFirstDayOfWeek(String curr) {
        DateTime dt = parseDate(curr);
        return getFirstDayOfWeek(dt, DEFAULT_FORMATTER);

    }

    public String getFirstDayOfWeek(String curr, String formatter) {
        DateTime dt = parseDate(curr);
        return getFirstDayOfWeek(dt, formatter);

    }

    private String getFirstDayOfWeek(DateTime dt, String formatter) {
        return dt.dayOfWeek().withMinimumValue().toString(formatter);

    }

}
