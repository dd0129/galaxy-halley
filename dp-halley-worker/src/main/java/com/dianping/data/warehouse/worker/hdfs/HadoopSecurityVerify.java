package com.dianping.data.warehouse.worker.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.SecurityUtil;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by hongdi.tang on 14-3-14.
 */
public class HadoopSecurityVerify {
    public static Configuration conf;

    private static Logger logger = LoggerFactory.getLogger(HadoopSecurityVerify.class);

    private static HadoopSecurityVerify securityLogin = null;

    private HadoopSecurityVerify() {

    }

    public static HadoopSecurityVerify getInstance(){
        if(HadoopSecurityVerify.securityLogin == null){
            return securityLogin = new HadoopSecurityVerify();
        }else {
            return securityLogin;
        }
    }

    public synchronized void verify(){
        if (conf == null){
            conf = new Configuration();
        }
        UserGroupInformation.setConfiguration(conf);
        try {
            SecurityUtil.login(conf, "test.hadoop.keytab.file", "test.hadoop.principal");
        }
        catch (IOException e) {
            logger.error("security verify error",e);
        }
    }

}
