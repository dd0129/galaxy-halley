/**
 * Project: canaan
 *
 * File Created at 2012-9-24
 * $Id$
 *
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.dianping.data.warehouse.core.common.SqlParser;

import com.dianping.data.warehouse.halley.client.Const;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * TODO Comment of Context
 *
 * @author yifan.cao
 */
public class CanaanConf {

    private static CanaanConf conf = null;
    private Map<String, String> optionConf = new HashMap<String, String>();
    private Map<String, String> envConf = new HashMap<String, String>();
    private Properties defaultConf = new Properties();
    private Properties canaanProp = null;


    public static CanaanConf getConf() {
        if (conf == null) {
            conf = new CanaanConf();
        }
        conf.init();
        return conf;
    }

    private void init() {
        loadEnvConf();
    }

    public void loadEnvConf() {
        Map<String, String> em = System.getenv();
        for (String k : em.keySet()) {
            envConf.put(k.toUpperCase(), em.get(k));
        }
    }

    public Properties getCanaanProperties() {
        if (this.canaanProp == null) {
            this.canaanProp = new Properties();

            for (Constants.BATCH_CAL_VARS var : Constants.BATCH_CAL_VARS
                    .values()) {
                String key = var.toString();
                String value = getCalVariables(key);
                this.canaanProp.setProperty(key, value);
            }

            for (Object key : defaultConf.keySet()) {
                if (defaultConf.getProperty(key.toString()) != null) {
                    this.canaanProp.setProperty(key.toString(),
                            defaultConf.getProperty(key.toString()));

                }
            }

            for (Object key : envConf.keySet()) {
                if (envConf.get(key.toString()) != null) {
                    this.canaanProp.setProperty(key.toString(),
                            envConf.get(key.toString()));
                }
            }

            for (Object key : optionConf.keySet()) {
                if (optionConf.get(key.toString()) != null) {
                    this.canaanProp.setProperty(key.toString(),
                            optionConf.get(key.toString()));
                }
            }
        }
        return this.canaanProp;
    }

    public String getCalVariables(String key) {
        // YYYYMMDD_P1D YYYYMMDD_P1D YYYYMMDD_YESTERDAY YYYYMMDD_DEFAULT_HP_DT
        String value = "";

        if ("YYYYMMDD_DEFAULT_HP_DT".equalsIgnoreCase(key))
            return Constants.DEFAULT_HP_DT;

        try {
            String[] v = key.split("_");
            int sign = 1;
            int offset = 0;
            SimpleDateFormat format = Constants.LONG_DF;
            int field = Calendar.DATE;

            if (v.length > 0) {
                String frmt = v[0];
                if ("YYYYMMDD".equalsIgnoreCase(frmt))
                    format = Constants.DAY_DF;
                else if ("YYYYMM".equalsIgnoreCase(frmt))
                    format = Constants.MONTH_DF;
                else if ("YYYY".equalsIgnoreCase(frmt))
                    format = Constants.YEAR_DF;
                else if ("MM".equalsIgnoreCase(frmt))
                    format = Constants.MM_DF;
                else if ("DD".equalsIgnoreCase(frmt))
                    format = Constants.DD_DF;
            }

            if (v.length > 1) {
                String s = v[1];

                if (s.toUpperCase().endsWith("TODAY")) {
                    sign = 1;
                    offset = 0;
                    return DateUtils.getFormatDateString(
                            Constants.DAY_DF.format(new Date()), field, offset
                                    * sign, format
                    );
                } else if (s.toUpperCase().endsWith("YESTERDAY")) {
                    sign = 1;
                    offset = -1;
                    return DateUtils.getFormatDateString(
                            Constants.DAY_DF.format(new Date()), field, offset
                                    * sign, format
                    );
                } else {
                    sign = s.startsWith("P") ? -1 : 1;
                    offset = Integer.valueOf(s.replaceAll("[^0-9]", ""));
                    if (s.toUpperCase().endsWith("DOWIM"))
                        field = Calendar.DAY_OF_WEEK_IN_MONTH;
                    else if (s.toUpperCase().endsWith("DOW"))
                        field = Calendar.DAY_OF_WEEK;
                    else if (s.toUpperCase().endsWith("DOM"))
                        field = Calendar.DAY_OF_MONTH;
                    else if (s.toUpperCase().endsWith("DOY"))
                        field = Calendar.DAY_OF_YEAR;
                    else if (s.toUpperCase().endsWith("D"))
                        field = Calendar.DATE;
                    else if (s.toUpperCase().endsWith("M"))
                        field = Calendar.MONTH;
                    else if (s.toUpperCase().endsWith("Y"))
                        field = Calendar.YEAR;
                    else if (s.toUpperCase().endsWith("WOY"))
                        field = Calendar.WEEK_OF_YEAR;
                }

            }

            String batch_cal_dt = getCanaanVariables(Constants.BATCH_COMMON_VARS.BATCH_CAL_DT
                    .toString());

            value = DateUtils.getFormatDateString(batch_cal_dt, field, offset
                    * sign, format);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }

    public String getCanaanVariables(String key) {
        String value;
        String optionValue = getOptionConfVariables(key);
        String envValue = getEnvConfVariables(key);

        if (optionValue != null) {
            value = optionValue;
        } else if (envValue != null) {
            value = envValue;
        } else {
            String defaultValue = getDefaultConfVariables(key);
            value = defaultValue;
        }
        return value;
    }

    public String getOptionConfVariables(String key) {
        return optionConf.get(key);
    }

    public String getEnvConfVariables(String key) {
        return envConf.get(key);
    }

    public String getDefaultConfVariables(String key) {
        if (key.equals(Constants.BATCH_COMMON_VARS.BATCH_CAL_DT.toString())) {
            return DateUtils
                    .getFormatDateString(Constants.DEFAULT_BATCH_CAL_DT_OFFSET);
        }
        return defaultConf.getProperty(key);
    }
}
