package com.dianping.data.warehouse.core.util;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by Sunny on 14-6-25.
 */
public class HalleyStrUtils {

    public static String concatPathStr(String... files){
        StringBuilder builder = new StringBuilder();
        builder.append(File.separator);
        for(String file : files){
            file = file.startsWith("\\") || file.startsWith("/") ? file.substring(1) : file;
            builder.append(file).append(File.separator);
        }
        String str = builder.toString();
        return str.substring(0,str.length()-1);
    }


}
