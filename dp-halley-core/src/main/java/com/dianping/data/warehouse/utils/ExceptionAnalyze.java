package com.dianping.data.warehouse.utils;

import com.dianping.data.warehouse.dao.ExceptionDAO;
import com.dianping.data.warehouse.domain.ExceptionAlertDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunny on 14-7-18.
 */
public class ExceptionAnalyze {

    private static Logger logger = LoggerFactory.getLogger(ExceptionAnalyze.class);

    @Resource
    private ExceptionDAO exceptionDAO;

    private Map<String, ExceptionAlertDO> getExceptionAlertsByProduct(String product) {
        Map<String, ExceptionAlertDO> alertMap = new HashMap<String, ExceptionAlertDO>();
        List<ExceptionAlertDO> exceptionAlertDOs = exceptionDAO.getExceptionAlertsByProduct(product);
        for (ExceptionAlertDO exceptionAlertDO : exceptionAlertDOs) {
            String reason = exceptionAlertDO.getDescription();
            alertMap.put(reason, exceptionAlertDO);
        }
        return alertMap;
    }

    public ExceptionAlertDO analyze(InputStream is, String product) {
        logger.warn("analysis log starts :"+product );
        Map<String, ExceptionAlertDO> exceptionAlertDOMap = getExceptionAlertsByProduct(product);
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                ExceptionAlertDO alert = analyzeLine(exceptionAlertDOMap, line);
                if (alert != null) {
                    logger.warn("exception log line: " + line);
                    return alert;
                }
            }
            return null;
        } catch (IOException ioe) {
            logger.error("analysis log error",ioe);
            return null;
        }finally{
            logger.warn("analysis log ens :" +product);
        }
    }

    private ExceptionAlertDO analyzeLine(Map<String, ExceptionAlertDO> alertMap, String line) {
        Iterator iter = alertMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if (line.toLowerCase().contains(key.toLowerCase()))
                return (ExceptionAlertDO) entry.getValue();
        }
        return null;
    }

    public static void main(String args[]) {
        File file = new File("/Users/Sunny/Desktop/test.txt");
        try {
            FileInputStream in = new FileInputStream(file);
            ExceptionAlertDO alertDO = new ExceptionAnalyze().analyze(in, "wormhole");

            System.out.println(alertDO.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
