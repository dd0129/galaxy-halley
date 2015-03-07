package com.dianping.data.warehouse.core.common;

/**
 * Created by Sunny on 14-7-3.
 */
public class CommonUtils {
    public static boolean equals(Object src, Object des) {
        if (src == null)
            return des == null;
        else
            return src.equals(des);
    }
}
