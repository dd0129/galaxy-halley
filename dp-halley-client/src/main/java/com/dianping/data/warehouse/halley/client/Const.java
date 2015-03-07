package com.dianping.data.warehouse.halley.client;


/**
 * Created by hongdi.tang on 14-4-1.
 */
public class Const {
    public static enum JOB_STATUS {
        JOB_FAIL(-1, "FAIL"), JOB_SUCCESS(1, "SUCCESS"), JOB_INIT(0, "INIT"), JOB_RUNNING(2, "RUNNING"), JOB_SUSPEND(3, "SUSPEND"),
        JOB_INTERNAL_ERROR(4, "INTERNAL_ERROR"), JOB_WAIT(5, "WAIT"), JOB_READY(6, "READY"), JOB_TIMEOUT(7, "TIMEOUT");
        private Integer value;
        private String desc;

        private JOB_STATUS(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static enum DQC_PARAM {
        task_id, schedule_time, task_status_id
    }

    public static enum RETURN_STR {
        code, message
    }

//    public static enum EXTERNAL_CODES {
//        SUCCESS(300),FAIL(301),EXCEPTION(500);
//        private Integer code;
//        private EXTERNAL_CODES(Integer code){
//            this.code = code;
//        }
//        public Integer getCode(){
//            return this.code;
//        }
//    }

    public static enum EXTERNAL_ERROR_CODES {
        DQC_FAIL(301);
        private Integer code;

        private EXTERNAL_ERROR_CODES(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }
    }

    public static enum EXTERNAL_SUCCESS_CODES {
        DQC_SUCCESSS(300);
        private Integer code;

        private EXTERNAL_SUCCESS_CODES(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }
    }

    public static int IF_YES = 1,
            IF_NO = 0;

    public static final String TASK_DATEBASE_SRC = "hive",
            TASK_LOG_HOME = "${calculate_log_home}",
            TASK_OFFSET_TYPE = "offset",
            TASK_OBJ = "sh /data/deploy/dwarch/conf/ETL/bin/randomipExecuter.sh";

    public static final int TASK_TYPE_CALCULATE = 2,
            TASK_TYPE_TRANSFER = 1;
}
