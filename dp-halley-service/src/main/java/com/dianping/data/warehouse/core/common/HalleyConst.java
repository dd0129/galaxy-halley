package com.dianping.data.warehouse.core.common;

import com.dianping.lion.client.ConfigCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-2-13.
 */
public class HalleyConst {
    /**
     * 数据源类型 - HIVE
     */
    public static final String DATABASE_TYPE_HIVE = "hive";

    /**
     * 数据源类型 - GP57
     */
    public static final String DATABASE_TYPE_GP57 = "gp57";

    /**
     * 数据源类型 - GP59
     */
    public static final String DATABASE_TYPE_GP59 = "gp59";

    public static final String HALLEY_CASCADE_TOPIC = "halley_cascade";

    public static final Map<String, String> HALLEY_DIRS = new HashMap<String, String>();

    public static final Integer TASK_EXISTS_PRE = 1,
                                TASK_NON_EXISTS_PRE = 0;

    /**
     * halley的目录变量
     */
    public static final String wormhole_home = "/data/deploy/dwarch/conf/ETL",
            wormhole_job_home = "/data/deploy/dwarch/conf/ETL/job",
            wormhole_log_home = "/data/deploy/dwarch/log/ETL",
            calculate_home = "/data/deploy/dwarch/conf/ETL",
            calculate_job_home = "/home/dwdev/work",
            calculate_log_home = "/data/deploy/dwarch/log/calculate";


    static {
        HALLEY_DIRS.put("${wormhole_home}", wormhole_home);
        HALLEY_DIRS.put("${wormhole_job_home}", wormhole_job_home);
        HALLEY_DIRS.put("${wormhole_log_home}", wormhole_log_home);
        HALLEY_DIRS.put("${calculate_home}", calculate_home);
        HALLEY_DIRS.put("${calculate_job_home}", calculate_job_home);
        HALLEY_DIRS.put("${calculate_log_home}", calculate_log_home);
    }

    /**
     * 任务运行实例状态
     */
    public static final Integer TASK_STATUS_UNKOWN = -3,
            TASK_STATUS_UNSUCCESS = -2,
            TASK_STATUS_FAIL = -1,
            TASK_STATUS_INIT = 0,
            TASK_STATUS_SUCCESS = 1,
            TASK_STATUS_RUNNING = 2,
            TASK_STATUS_SUSPEND = 3,
            TASK_STATUS_INIT_ERROR = 4,
            TASK_STATUS_WAIT = 5,
            TASK_STATUS_READY = 6,
            TASK_STATUS_TIMEOUT = 7;

    public static final Integer TASK_GROUP_ID_DPODS= 1;

    public static final String logDir = "/data/deploy/dwarch/log";
    public static final String logUrl = "http://10.1.6.151";

    public static final String LION_LOG_URL = "galaxy.log_url";

    public enum PublishReturnCode{
        publishSuccess(1),fileNotExists(2),parseTaskDependencyFail(3),
        gitFail(4),hdfsFail(5),publishFail(6),unknowFail(7);
        public int getCode() {
            return code;
        }

        private int code;
        private PublishReturnCode(int code){
            this.code = code;
        }
    }
    public static Integer PUBLISH_SUCCESS = 1,
            PUBLISH_FAILURE = 0;

    public static String DEPLOY_IP;
    public static Integer DEPLOY_PORT;
    public static String DEPLOY_USER;
    public static String COMMAND_CANAAN;
    public static String IDENTITY_FILE;
    public static String COMMAND_SUN;
    private static ConfigCache configCache;


//    static {
//        try {
//            configCache = ConfigCache.getInstance(EnvZooKeeperConfig.getZKAddress());
//            DEPLOY_IP = configCache.getProperty("deploy_ip");
//            DEPLOY_PORT = Integer.parseInt(configCache.getProperty("deploy_port"));
//            DEPLOY_USER = configCache.getProperty("deploy_user");
//            COMMAND_CANAAN = configCache.getProperty("command_canaan");
//            IDENTITY_FILE = configCache.getProperty("identity_file");
//            COMMAND_SUN = configCache.getProperty("command_sun");
//        } catch (LionException e) {
//            //logger.error("load mapping file or lion cfg failure",e);
//            e.printStackTrace();
//        }
//    }

}
