package com.dianping.data.warehouse.halley.domain;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by hongdi.tang on 14-6-17.
 */
public class PublishFileDO implements Serializable {

    private static final long serialVersionUID = 2867745590025783560L;
    private static final String PUBLISH_ID_PREFIX = "publishID_";

    private String filename;
    private String projectName;
    private String publishId;
    private String host;
    private boolean flag;
    private String timestamp;
    private Long firstReceivedTimeMills;


    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public Long getFirstReceivedTimeMills() {
        return firstReceivedTimeMills;
    }

    public void setFirstReceivedTimeMills(Long firstReceivedTimeMills) {
        this.firstReceivedTimeMills = firstReceivedTimeMills;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public PublishFileDO(String projectName, String fileName){
        this.publishId = this.generatePublishID();
        this.projectName = projectName;
        this.filename = fileName;
    }

    public PublishFileDO(){}

    private synchronized String generatePublishID(){
        Random random = new Random();
        int num = random.nextInt(1000);
        return PUBLISH_ID_PREFIX + new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()) + "_" + num;
    }
}
