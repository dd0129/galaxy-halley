package com.dianping.data.warehouse.core.util;

import com.dianping.lion.EnvZooKeeperConfig;
import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.LionException;

/**
 * Created by Sunny on 14/11/13.
 */
public class LionUtils {

    public static String getValue(String key) {
        ConfigCache configCache = null;
        try {
            configCache = ConfigCache.getInstance(EnvZooKeeperConfig.getZKAddress());
            return configCache.getProperty(key);
        } catch (LionException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}
