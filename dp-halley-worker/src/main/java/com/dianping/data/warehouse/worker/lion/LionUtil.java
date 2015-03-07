package com.dianping.data.warehouse.worker.lion;

import com.dianping.lion.EnvZooKeeperConfig;
import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.LionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lion工具类
 */
public class LionUtil {

    private static final Logger LOG = LoggerFactory.getLogger(LionUtil.class);

    private static ConfigCache configCache;

    static {
        try {
            configCache = ConfigCache.getInstance(EnvZooKeeperConfig.getZKAddress());
        } catch (LionException ex) {
            String exMsg = "Lion连接异常";
            LOG.error(exMsg, ex);
            throw new RuntimeException(exMsg, ex);
        }
    }

    /**
     * 获取String属性
     * 
     * @param key
     * @return String
     */
    public static String getProperty(String key) {
        String exMsg = "无法获取Lion属性【" + key + "】";
        try {
            String value = configCache.getProperty(key);
            if (value == null) {
                throw new RuntimeException(exMsg);
            }
            LOG.info(key + "=" + value);
            return value;
        } catch (LionException ex) {
            // TODO add sms notification
            LOG.error(exMsg, ex);
            throw new RuntimeException(exMsg, ex);
        }
    }

    /**
     * 获取Integer属性
     * 
     * @param key
     * @return Integer
     */
    public static Integer getIntProperty(String key) {
        String exMsg = "无法获取Lion属性【" + key + "】";
        try {
            Integer value = configCache.getIntProperty(key);
            if (value == null) {
                throw new RuntimeException(exMsg);
            }
            LOG.info(key + "=" + value);
            return value;
        } catch (LionException ex) {
            // TODO add sms notification
            LOG.error(exMsg, ex);
            throw new RuntimeException(exMsg, ex);
        }
    }

}