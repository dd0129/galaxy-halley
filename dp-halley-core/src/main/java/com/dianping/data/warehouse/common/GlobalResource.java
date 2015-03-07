package com.dianping.data.warehouse.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hongdi.tang on 14-1-16.
 */
public class GlobalResource {
    private GlobalResource(){}
    private static final Logger logger = LoggerFactory.getLogger(GlobalResource.class);

    public static String DEPLOY_HOME;
    private static final String CFG_FILE = "conf/env.properties";
    public static Map<String,String> ENV_PROPS = new HashMap<String,String>();

    static {
        DEPLOY_HOME = System.getenv("deploy_home");
        logger.info("deploy_home :=" + DEPLOY_HOME);
        if(DEPLOY_HOME == null){
            throw new NullPointerException("deploy home is null");
        }
        Properties props = new Properties();
        try{
            props.load(GlobalResource.class.getClassLoader().getResourceAsStream(CFG_FILE));
            for (String key : props.stringPropertyNames()){
                ENV_PROPS.put(key,props.getProperty(key).replace("${deploy_home}", DEPLOY_HOME));
            }
            ENV_PROPS.put("${deploy_home}",DEPLOY_HOME);
        }catch(Exception e){
            logger.error("load env error",e);
            throw new Error("load env error");
        }
    }

    public void print(){
        System.out.println("222222");

    }

}
