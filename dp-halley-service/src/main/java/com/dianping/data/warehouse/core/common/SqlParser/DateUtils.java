/**
 * Project: canaan
 * 
 * File Created at Oct 10, 2012
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO Comment of DateUtils
 * 
 * @author stephensonz
 * 
 */
public class DateUtils {
	public static Date getDate(String str) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String clearnStr = str.replaceAll("[^0-9]", "");
		Date d = df.parse(clearnStr);
		return d;
	}

	public static String getFormatDateString(int offset) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, offset);
		return Constants.DAY_DF.format(c.getTime());
	}

	public static String getFormatDateString(String str, int offset) throws ParseException {
		Date d = getDate(str);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, offset);
		return Constants.DAY_DF.format(c.getTime());
	}

	public static String getFormatDateString(String str) throws ParseException {
		return getFormatDateString(str, 0);
	}


	public static String getFormatDateString(String str, int cal_field, int offset, SimpleDateFormat sdf)
			throws ParseException {
		Date d = getDate(str);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(cal_field, offset);
		return sdf.format(c.getTime());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			try {
				String[] strs = br.readLine().split(" ");
				String str = DateUtils.getFormatDateString(strs[0]);
				String str2 = DateUtils.getFormatDateString(strs[0], Integer.parseInt(strs[1]));
				System.out.println(str);
				System.out.println(str2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
