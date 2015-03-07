package com.dianping.data.warehouse.worker.common;

import com.dianping.data.warehouse.worker.lion.LionUtil;

/**
 * Created by hongdi.tang on 14-6-17.
 */
public class Const {
    public static final String localPublishPath = "d:/data/test";



    public static final String HDFS_DIR = LionUtil.getProperty("galaxy-halley.HDFS_DIR");

    public static final String MASTER_GIT_DIR = "d:/data/test";
    public static final String DATAANALYSIS_PUBLISH_BASE_DIR = "/data/deploy/publish_test",
                               WAREHOUSE_PUBLISH_BASE_DIR = "/data/deploy/warehouse_test";


    public static Integer PUBLISH_SUCCESS = 1,
                          PUBLISH_FAILURE = 0;
}
