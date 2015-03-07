package com.dianping.data.warehouse.external;

import com.dianping.data.warehouse.common.CoreConst;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-4-4.
 */

public class ExternalClassloaderTest {
    @Test
    public void testLoadClass() throws Exception{
//        ClassLoader loader = new ExternalClassloader(CoreConst.EXTERNAL_CLASSPATH);
//        Class clazz = loader.loadClass("com.dianping.data.warehouse.external.Test");
//
//        Method method = clazz.getMethod("execute");
//        method.invoke(clazz.newInstance());
//        Class clazz1 = loader.loadClass(CoreConst.DQC_CLASS);
//        Method method1 = clazz1.getMethod("run", Map.class);
//        method1.invoke(clazz.newInstance(),new HashMap<String,String>());

        ClassLoaderUtil.loadJarPath(CoreConst.EXTERNAL_CLASSPATH);
        Class clazz2 = Class.forName(CoreConst.DQC_CLASS);
        Method method2 = clazz2.getMethod("run",Map.class);
        method2.invoke(clazz2.newInstance(),new HashMap<String,String>());

//        ClassLoaderUtil.loadJarPath(CoreConst.EXTERNAL_CLASSPATH);
//        Class clazz3 = Class.forName(CoreConst.DQC_CLASS);
//        Method method4 = clazz3.getMethod("run",Map.class);
//        method2.invoke(clazz3.newInstance(),new HashMap<String,String>());



    }
}
