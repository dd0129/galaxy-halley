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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO Comment of Constants
 * 
 * @author yifan.cao
 * 
 */
public class Constants {
	// Constants for RETURN CODE
	public static final int RET_SUCCESS = 0;
	public static final int RET_FAILED = 1;

	// Constant dateformat
    public static final SimpleDateFormat LONG_DF_FOR_FILENAME = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat LONG_DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat SHORT_DF = new SimpleDateFormat("HH:mm:ss.SSS");
	public static final SimpleDateFormat DAY_DF = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat MONTH_DF = new SimpleDateFormat("yyyy-MM");
	public static final SimpleDateFormat YEAR_DF = new SimpleDateFormat("yyyy");
	public static final SimpleDateFormat MM_DF = new SimpleDateFormat("MM");
	public static final SimpleDateFormat DD_DF = new SimpleDateFormat("dd");

	public static final String DEFAULT_HP_DT = "3000-12-31";
	public static final int BATCH_RTN_SUCCESS = 0;
	public static final int BATCH_RTN_FAILURE = 1;

	public static final String PARAM_IN_H = "h";
	public static final String PARAM_IN_C = "c";
	public static final String PARAM_IN_DOL = "dol";
    public static final String PARAM_IN_STR = "str";
	public static final String PARAM_IN_D = "d";
	public static final String PARAM_IN_S = "s";
	public static final String PARAM_IN_U = "u";
	public static final String PARAM_IN_TID = "tid";
	public static final String PARAM_IN_EXT = "E";
    public static final String PARAM_IN_P = "p";

	public static final char PARAM_IN_EXT_DELIMITER = '=';

	public static final String PARAM_IN_DESC_H = "short help";
	public static final String PARAM_IN_DESC_C = "Caculator name";
	public static final String PARAM_IN_DESC_DOL = "dol filename";
	public static final String PARAM_IN_DESC_D = "date input";
	public static final String PARAM_IN_DESC_S = "serverid: 5|6|7";
	public static final String PARAM_IN_DESC_U = "user type: dwdev|bi";
	public static final String PARAM_IN_DESC_TID = "scheduling task id";
	public static final String PARAM_IN_DESC_EXT = "user defined parameters";
    public static final String PARAM_IN_DESC_P = "only parse the dol and print sql";
    public static final String PARAM_IN_DESC_STR = "Text for dol";

	public static final String PARAM_IN_DESC_EXT_ARG = "paramN=valN";

    public static final String DOL_TYPE_DOL = "FILE";
    public static final String DOL_TYPE_STR = "STR";

    public static final Map<String, String> param2DescMapping = new HashMap<String, String>();


    static {
		Constants.param2DescMapping.put(Constants.PARAM_IN_H, Constants.PARAM_IN_DESC_H);
		Constants.param2DescMapping.put(Constants.PARAM_IN_C, Constants.PARAM_IN_DESC_C);
		Constants.param2DescMapping.put(Constants.PARAM_IN_DOL, Constants.PARAM_IN_DESC_DOL);
		Constants.param2DescMapping.put(Constants.PARAM_IN_D, Constants.PARAM_IN_DESC_D);
		Constants.param2DescMapping.put(Constants.PARAM_IN_S, Constants.PARAM_IN_DESC_S);
		Constants.param2DescMapping.put(Constants.PARAM_IN_U, Constants.PARAM_IN_DESC_U);
		Constants.param2DescMapping.put(Constants.PARAM_IN_TID, Constants.PARAM_IN_DESC_TID);
        Constants.param2DescMapping.put(Constants.PARAM_IN_P, Constants.PARAM_IN_DESC_P);
        Constants.param2DescMapping.put(Constants.PARAM_IN_STR, Constants.PARAM_IN_DESC_STR);
	}

	public static enum BATCH_SERVERS {
		HIVE_PROD, HIVE_DEV, HIVE_TEST
	}

	public static final Map<String, String> id2serverMapping = new HashMap<String, String>();
	static {
		Constants.id2serverMapping.put("5", BATCH_SERVERS.HIVE_PROD.toString());
		Constants.id2serverMapping.put("6", BATCH_SERVERS.HIVE_DEV.toString());
		Constants.id2serverMapping.put("7", BATCH_SERVERS.HIVE_TEST.toString());
	}

	public static enum BATCH_COMMON_VARS {
		BATCH_CAL_DT, BATCH_DOL, BATCH_CACULATOR, BATCH_SERVER, BATCH_USER, CANAAN_HOME, CANAAN_CONF_SUBDIR, CANAAN_CONF_DIR, BATCH_DOL_DIR, BATCH_LOG_DIR, BATCH_MYJAR_DIR, BATCH_HIVE_CLIENT, BATCH_TASK_ID,
		BATCH_HIVE_INIT_DIR,BATCH_PARSE_ONLY,BATCH_DOL_TYPE,BATCH_DOL_STR
    }

	public static final String COMMON_VARS_CAL_PREFIX = "CAL_"; /*
																 * all the
																 * system
																 * environmental
																 * cal variable
																 * names SHOULD
																 * start with
																 * this String
																 */

	public static enum BATCH_CAL_VARS {
		CAL_YYYYMMDD_YESTERDAY,CAL_YYYYMMDD_TODAY,CAL_YYYYMMDD_DEFAULT_HP_DT,
		CAL_YYYYMM_YESTERDAY,CAL_YYYYMM_TODAY,CAL_YYYY_YESTERDAY,CAL_YYYY_TODAY,
		CAL_MM_YESTERDAY,CAL_MM_TODAY,CAL_DD_YESTERDAY,CAL_DD_TODAY,CAL_YYYYMMDD,CAL_MM,CAL_DD, 
		CAL_YYYYMMDD_P1D, CAL_YYYYMMDD_P2D,CAL_YYYYMMDD_P3D, CAL_YYYYMMDD_P4D,CAL_YYYYMMDD_P5D,CAL_YYYYMMDD_P6D, CAL_YYYYMMDD_P7D,
	    CAL_YYYYMMDD_P8D, CAL_YYYYMMDD_P9D,CAL_YYYYMMDD_P10D, CAL_YYYYMMDD_P11D,CAL_YYYYMMDD_P12D,CAL_YYYYMMDD_P13D, CAL_YYYYMMDD_P14D,
	    CAL_YYYYMMDD_P15D, CAL_YYYYMMDD_P16D,CAL_YYYYMMDD_P17D, CAL_YYYYMMDD_P18D,CAL_YYYYMMDD_P19D,CAL_YYYYMMDD_P20D, CAL_YYYYMMDD_P21D,
	    CAL_YYYYMMDD_P22D, CAL_YYYYMMDD_P23D,CAL_YYYYMMDD_P24D, CAL_YYYYMMDD_P25D,CAL_YYYYMMDD_P26D,CAL_YYYYMMDD_P27D, CAL_YYYYMMDD_P28D,CAL_YYYYMMDD_P29D,CAL_YYYYMMDD_P30D,
		CAL_YYYYMMDD_P1M, CAL_YYYYMMDD_P2M, CAL_YYYYMMDD_P3M, CAL_YYYYMMDD_P4M,CAL_YYYYMMDD_P5M, CAL_YYYYMMDD_P6M, CAL_YYYYMMDD_P7M, CAL_YYYYMMDD_P8M, CAL_YYYYMMDD_P9M, CAL_YYYYMMDD_P10M, CAL_YYYYMMDD_P11M, CAL_YYYYMMDD_P12M, 
		CAL_YYYYMMDD_P1Y, CAL_YYYYMMDD_P2Y, CAL_YYYYMM, CAL_YYYYMM_P1D, CAL_YYYYMM_P2D, CAL_YYYYMM_P1M, CAL_YYYYMM_P2M, CAL_YYYYMM_P1Y, CAL_YYYYMM_P2Y, CAL_YYYY, CAL_YYYY_P1D, CAL_YYYY_P2D, CAL_YYYY_P1M, CAL_YYYY_P2M, CAL_YYYY_P1Y, CAL_YYYY_P2Y, CAL_YYYYMMDD_N1D, CAL_YYYYMMDD_N2D, CAL_YYYYMMDD_N1M, CAL_YYYYMMDD_N2M, CAL_YYYYMMDD_N1Y, CAL_YYYYMMDD_N2Y, CAL_YYYYMM_N1D, CAL_YYYYMM_N2D, CAL_YYYYMM_N1M, CAL_YYYYMM_N2M, CAL_YYYYMM_N1Y, CAL_YYYYMM_N2Y, CAL_YYYY_N1D, CAL_YYYY_N2D, CAL_YYYY_N1M, CAL_YYYY_N2M, CAL_YYYY_N1Y, CAL_YYYY_N2Y, CAL_YYYYMMDD_P1DOW, CAL_YYYYMMDD_P2DOW, CAL_YYYYMMDD_P3DOW, CAL_YYYYMMDD_P4DOW, CAL_YYYYMMDD_P5DOW, CAL_YYYYMMDD_P6DOW, CAL_YYYYMMDD_P7DOW, CAL_YYYYMMDD_P8DOW, CAL_YYYYMMDD_P9DOW, CAL_YYYYMMDD_P10DOW, CAL_YYYYMM_P1DOW, CAL_YYYYMM_P2DOW, CAL_YYYY_P1DOW, CAL_YYYY_P2DOW, CAL_YYYYMMDD_P1DOM, CAL_YYYYMMDD_P2DOM, CAL_YYYYMM_P1DOM, CAL_YYYYMM_P2DOM, CAL_YYYY_P1DOM, CAL_YYYY_P2DOM, CAL_YYYYMMDD_P1DOY, CAL_YYYYMMDD_P2DOY, CAL_YYYYMM_P1DOY, CAL_YYYYMM_P2DOY, CAL_YYYY_P1DOY, CAL_YYYY_P2DOY, CAL_YYYYMMDD_P1DOWIM, CAL_YYYYMMDD_P2DOWIM, CAL_YYYYMM_P1DOWIM, CAL_YYYYMM_P2DOWIM, CAL_YYYY_P1DOWIM, CAL_YYYY_P2DOWIM;

		public String toString() {
			return this.name().replace(Constants.COMMON_VARS_CAL_PREFIX, "");
		}
	}

	public static final Map<String, String> param2ContextVarMapping = new HashMap<String, String>();

	static {
		Constants.param2ContextVarMapping.put(Constants.PARAM_IN_D, BATCH_COMMON_VARS.BATCH_CAL_DT.toString());
		Constants.param2ContextVarMapping.put(Constants.PARAM_IN_DOL, BATCH_COMMON_VARS.BATCH_DOL.toString());
		Constants.param2ContextVarMapping.put(Constants.PARAM_IN_C, BATCH_COMMON_VARS.BATCH_CACULATOR.toString());
		Constants.param2ContextVarMapping.put(Constants.PARAM_IN_S, BATCH_COMMON_VARS.BATCH_SERVER.toString());
		Constants.param2ContextVarMapping.put(Constants.PARAM_IN_U, BATCH_COMMON_VARS.BATCH_USER.toString());
		Constants.param2ContextVarMapping.put(Constants.PARAM_IN_TID, BATCH_COMMON_VARS.BATCH_TASK_ID.toString());
        Constants.param2ContextVarMapping.put(Constants.PARAM_IN_P, BATCH_COMMON_VARS.BATCH_PARSE_ONLY.toString());
        Constants.param2ContextVarMapping.put(Constants.PARAM_IN_STR, BATCH_COMMON_VARS.BATCH_DOL_STR.toString());
	}

	public static enum BATCH_USERS {
		DWDEV, BI
	}

	public static final String DEFAULT_CONF_NAME = "canaan.property.defaultConf";
	public static int DEFAULT_BATCH_CAL_DT_OFFSET = -1;

	public static final String MYSQL_CONN_LOGIN_VARS_PREFIX = "MYSQL_LOGIN_"; /*
																			 * all
																			 * the
																			 * mysql
																			 * login
																			 * variable
																			 * names
																			 * SHOULD
																			 * start
																			 * with
																			 * this
																			 * String
																			 */

	public static enum MYSQL_CONNECT_PARAMS {
		SWITCH, USERNAME, PASSWORD, HOST, PORT, DATABASE;

		public String toString() {
			StringBuilder sb = new StringBuilder().append(Constants.MYSQL_CONN_LOGIN_VARS_PREFIX).append(this.name());
			return sb.toString();
		}
	}
	
	// No SQL Hive prefix
	public static final String[] NOSQL_PREFIX1 = { "quit", "exit", "source",
		"!", "list" };
	public static final String[] NOSQL_PREFIX2 = { "set", "dfs", "add",
		"delete" };
}
