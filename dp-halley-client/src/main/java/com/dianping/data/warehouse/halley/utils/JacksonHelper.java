package com.dianping.data.warehouse.halley.utils;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @Author <a href="mailto:tsensue@gmail.com">dishu.chen</a>
 * 13-12-24.
 */
public class JacksonHelper {
    private static Logger logger = Logger.getLogger(JacksonHelper.class);
    private static ObjectMapper mapper = null;

    private static ObjectMapper newObjectMapper() {
        if (null == mapper) {
            init();
        }
        return mapper;
    }

    private static synchronized void init() {
        if (null == mapper) {
            mapper = new ObjectMapper();
        }
    }

    public static String pojoToJson(Object obj) {
        StringWriter sw = new StringWriter();
        try {
            JacksonHelper.newObjectMapper().writeValue(sw, obj);
        } catch (Exception e) {
            logger.error("Generate obj to json failure.", e);
        }
        if (sw != null) {
            try {
                sw.flush();
                sw.close();
            } catch (IOException e) {
                logger.error("Flush failure.", e);
            }
        }
        return sw.toString();
    }

    public static <T> T jsonToPojo(String data, Class clazz) {
        try {
            return (T) JacksonHelper.newObjectMapper().readValue(data.getBytes("utf-8"), clazz);
        } catch (IOException e) {
            logger.error("Convert json to object error:" + data, e);
        }

        return null;
    }
}
