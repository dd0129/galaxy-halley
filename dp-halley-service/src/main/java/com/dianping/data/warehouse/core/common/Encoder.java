package com.dianping.data.warehouse.core.common;

/**
 * Created by Sunny on 14-6-3.
 */

import java.io.UnsupportedEncodingException;

/**
 * Created by Sunny on 14-6-3.
 */

public class Encoder {

    public static String encode(String content) {
        try {
            return new String(content.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}