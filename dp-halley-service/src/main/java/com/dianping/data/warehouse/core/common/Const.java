package com.dianping.data.warehouse.core.common;


import com.dianping.data.warehouse.core.lion.LionUtil;

/**
 * Created by hongdi.tang on 14-6-17.
 */
public class Const {
    public static final String localPublishPath = "d:/data/test";

    public static final String PUBLISH_ID_PREFIX = "publishID_";

    public static final String HDFS_DIR = LionUtil.getProperty("galaxy-halley.HDFS_DIR");

    public static final String MASTER_GIT_DIR = LionUtil.getProperty("galaxy-halley.MASTER_GIT_DIR");

    public static final String WORKER_PUBLISH_BASE_DIR = "/data/deploy";

    public static Integer PUBLISH_SUCCESS = 1,
            PUBLISH_FAILURE = 0;

    public static final String PROJECTNAME = "galaxy-halley",
            HIVE_CONNECTION_CFG = "hive_connection_cfg",
            HDFS_CONNECTION_CFG = "hdfs_connection_cfg",
            HADOOP_CONNECTION_CFG = "hadoop_connection_cfg";

    public static final String MAIL_SUBJECT = "你的任务被级联重跑",
            MAIL_SMTP_HOST_LABEL = "mail.smtp.host",
            MAIL_SMTP_HOST = "10.100.100.101",
            MAIL_SMTP_AUTH_LABEL = "mail.smtp.auth",
            MAIL_SMTP_AUTH = "false",
            MAIL_51PING = "dpbi@51ping.com";

    public static final int INSTANCE_STATUS_FAIL = -1,
            INSTANCE_STATUS_INIT = 0,
            INSTANCE_STATUS_SUCCESS = 1,
            INSTANCE_STATUS_RUNNING = 2,
            INSTANCE_STATUS_SUSPEND = 3,
            INSTANCE_STATUS_INITERROR = 4,
            INSTANCE_STATUS_WAIT = 5,
            INSTANCE_STATUS_READY = 6,
            INSTANCE_STATUS_TIMEOUT = 7;

    public static final String LION_ACL_RUL = "galaxy.acl_rul",
            LION_ACL_AK_ID = "galaxy.acl_ak_id";

    public static final String ACL_ADDRESS_PRODUCT = "http://data.dp/pluto/json",
            ACL_ADMIN_KEY_PRODUCT = "875";

}
