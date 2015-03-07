package com.dianping.data.warehouse.core.hdfs;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by adima on 14-6-14.
 */
public class HdfsUtils {
    private static Logger logger = LoggerFactory.getLogger(HdfsUtils.class);

    public static FileSystem hdfs;

    static {
        HadoopSecurityVerify.getInstance().verify();
        try {
            hdfs = FileSystem.get(HadoopSecurityVerify.conf);
        } catch (IOException e) {
            logger.error("HDFS get conf fail",e);
        }
    }

    public static boolean download(String hdfsFile, String localPath) {
        try {

            hdfs.copyToLocalFile(false, new Path(hdfsFile), new Path(localPath));
            return true;
        } catch (Exception e) {
            logger.error("download file: "+hdfsFile+" from hdfs fail",e);
            return false;
        }
    }

    public static boolean upload(String file, String hdfsFile) {
        try {
            hdfs.copyFromLocalFile(false, true, new Path(file), new Path(hdfsFile));
            return true;
        } catch (Exception e) {
            logger.error("hdfs upload file: " + hdfsFile + " failure", e);
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(HdfsUtils.class.getResource("/").getPath());
        try {
            HdfsUtils.upload("d:/555.xml", "/tmp/55.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
