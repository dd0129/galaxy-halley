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

import org.apache.commons.lang.text.StrBuilder;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * TODO Comment of SQLParser
 *
 * @author leo.chen
 */
public class SQLParser {
    private static final Logger LOG = Logger.getLogger(SQLParser.class);

    private DOLite dolite;
    private Map<Integer, AnalyzeResult> pedigreeAnalyzeResult;
    private PedigreeAnalyzer pa;
    private boolean isDolite;
    private String command;

    public static final String[] NOSQL_PREFIX1 = {"quit", "exit", "source",
            "!", "list"};
    public static final String[] NOSQL_PREFIX2 = {"set", "dfs", "add",
            "delete"};

    public SQLParser(DOLite dolite) {
        this.dolite = dolite;
        this.isDolite = true;
        init();
    }

    private void init() {
        pa = PedigreeAnalyzer.getAnalyzer();
        pedigreeAnalyzeResult = new HashMap<Integer, AnalyzeResult>();
    }

    private boolean canParse(String sql) {
        // filter like "set mapred.reduce.tasks=100"
        for (String prefix : NOSQL_PREFIX1) {
            if (sql.trim().toLowerCase().startsWith(prefix)) {
                return false;
            }
        }
        for (String prefix : NOSQL_PREFIX2) {
            if (sql.trim().toLowerCase().startsWith(prefix)) {
                return false;
            }
        }
        return true;
    }

    public Map<Integer, AnalyzeResult> parse() throws ParseException {
        //if (isDolite == true) {
        for (int line = 1; line <= dolite.size(); line++) {
            if (canParse(dolite.get(line))) {
                pedigreeAnalyzeResult.put(line,
                        pa.analyze(dolite.get(line)));

            }
        }

        // } else {
        //     if (canParse(this.command))
        //         pedigreeAnalyzeResult.put(0, pa.analyze(this.command));
        // }
        return pedigreeAnalyzeResult;
    }
}
