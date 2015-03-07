package com.dianping.data.warehouse.common;

import com.dianping.data.warehouse.utils.LionUtil;

public class CoreConst {

    public static enum TASK_TYPE {
        WORMHOLE(1), CALCULATE(2);

        private Integer value;

        private TASK_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public static enum TASK_CYCLE {
        M, D, W, H, mi;
    }

    public static enum TASK_IFWAIT {
        WAITED(1), UNWAITED(0);
        private Integer value;

        private TASK_IFWAIT(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public static enum TASK_IFPRE {
        EXISTS_PRE(1), NON_PRE(0);
        private Integer value;

        private TASK_IFPRE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public static enum TASK_IFRECALL {
        RECALL(1), NON_RECALL(0);
        private Integer value;

        private TASK_IFRECALL(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public final static String regex = "return code-";
    public final static int WORMHOLE_RUNNING_MAX_SIZE = 50;
    public final static int INTERNAL_EXECUTE_ERROR = -100;
    public final static int TASK_VALIDATE = 1;
    public final static int TASK_INVALIDATE = 1;
    public final static Integer TASK_TYPE_LOAD = 1;
    public final static Integer TASK_TYPE_CALCULATE = 2;

    public final static int WORMHOLE_UNKNOWN_EXCEPTION = 16;
    public final static int CALCULATE_UNKNOWN_EXCEPTION = 19;

    public final static Integer TASK_EXISTS_PRE = 1;
    public final static Integer TASK_NONEXISTS_PRE = 0;

    public final static Integer TASK_IF_WAIT = 1;
    public final static Integer DEFAULT_TASK_JOBCODE = -1;

    public final static String EXTERNAL_CLASSPATH = "/data/deploy/dp-halley/external";

    public final static String DQC_CLASS = "com.dianping.dqc.galaxy.TaskServiceDqcImpl";

    public final static String TASK_LOAD_TYPE_DESC  = "wormhole";
    public final static String TASK_CALCULATE_TYPE_DESC  = "calculate";

    public static Integer PRE_HOUR_LION;
    static{
        try{
            PRE_HOUR_LION = LionUtil.getIntProperty("galaxy-halley.PRE_HOUR");
        }catch(Exception e){
            PRE_HOUR_LION = 3 * 60 * 60 * 1000;
        }
    }
    public static Integer PRE_HOUR = PRE_HOUR_LION;

    public final static long MIN_LOG_SIZE=450L;
    public final static long MAX_HUNG_TIME=1000 * 60 * 20;
}
