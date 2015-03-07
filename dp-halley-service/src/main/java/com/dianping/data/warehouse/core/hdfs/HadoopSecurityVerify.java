package com.dianping.data.warehouse.core.hdfs;

import com.dianping.data.warehouse.core.common.Const;
import com.dianping.lion.EnvZooKeeperConfig;
import com.dianping.lion.client.ConfigCache;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.SecurityUtil;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hongdi.tang on 14-3-14.
 */
public class HadoopSecurityVerify {
    public static Configuration conf;

    private static Logger logger = LoggerFactory.getLogger(HadoopSecurityVerify.class);

    private static HadoopSecurityVerify securityLogin = null;

    private HadoopSecurityVerify() {

    }

    public static HadoopSecurityVerify getInstance() {
        if (HadoopSecurityVerify.securityLogin == null) {
            return securityLogin = new HadoopSecurityVerify();
        } else {
            return securityLogin;
        }
    }

    public synchronized void verify() {
        if (conf == null) {
            conf = new Configuration();
        }
        UserGroupInformation.setConfiguration(conf);
        try {
            ConfigCache configCache = ConfigCache.getInstance(EnvZooKeeperConfig.getZKAddress());
            String hive_cfg_str = configCache.getProperty(Const.PROJECTNAME + "." + Const.HIVE_CONNECTION_CFG);
            List<String> hive_cfg_list = Arrays.asList(hive_cfg_str.split(";"));
            for (String lion_key : hive_cfg_list) {
                String hive_key = StringUtils.substringAfterLast(lion_key, "galaxy-halley.");
                String hive_value = configCache.getProperty(lion_key);
                logger.info(hive_key + ":= " + hive_value);
                conf.set(hive_key, hive_value);
            }
            String hdfs_cfg_str = configCache.getProperty(Const.PROJECTNAME + "." + Const.HDFS_CONNECTION_CFG);
            List<String> hdfs_cfg_list = Arrays.asList(hdfs_cfg_str.split(";"));
            for (String lion_key : hdfs_cfg_list) {
                String hive_key = StringUtils.substringAfterLast(lion_key, "galaxy-halley.");
                String hive_value = configCache.getProperty(lion_key);
                logger.info(hive_key + ":= " + hive_value);
                conf.set(hive_key, hive_value);
            }
            String hadoop_cfg_str = configCache.getProperty(Const.PROJECTNAME + "." + Const.HADOOP_CONNECTION_CFG);
            List<String> hadoop_cfg_list = Arrays.asList(hadoop_cfg_str.split(";"));
            for (String lion_key : hadoop_cfg_list) {
                String hive_key = StringUtils.substringAfterLast(lion_key, "galaxy-halley.");
                String hive_value = configCache.getProperty(lion_key);
                logger.info(hive_key + ":= " + hive_value);
                conf.set(hive_key, hive_value);
            }
        } catch (Exception e) {
            logger.error("get pigeon conf error", e);
//            conf = GlobalResources.configuration;
        }
        try {
            SecurityUtil.login(conf, "test.hadoop.keytab.file", "test.hadoop.principal");
        } catch (IOException e) {
            logger.error("security verify error", e);
        }
    }

}
