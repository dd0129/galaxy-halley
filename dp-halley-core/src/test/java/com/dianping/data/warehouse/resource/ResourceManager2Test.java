package com.dianping.data.warehouse.resource;

import com.dianping.data.warehouse.common.GlobalResource;
import com.dianping.data.warehouse.common.MockData;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.utils.ProcessUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-5-26.
 */
public class ResourceManager2Test {

    public void setup() throws Exception{
        InstanceDO inst = new InstanceDO();
        inst.setInstanceId("1001");
        inst.setTaskId(1001);
        inst.setDatabaseSrc("hive");
        inst.setType(2);
        ResourceManager2.inQueue(inst);
    }

    @Test
    public void testInQueue() throws Exception {
        for(;;){
            InstanceDO inst = MockData.genInstance();
            //String instId = String.valueOf(Math.floor(Math.random() * 10000));
            Double d = Math.random() * 10000;
            int a = d.intValue();
            System.out.println(a);

            inst.setInstanceId(String.valueOf(a));
            inst.setTaskId(a);
            inst.setDatabaseSrc("hive");
            ResourceManager2.inQueue(inst);
        }

    }

    @Test
    public void testResourceStream() throws Exception{
        Map<String,Integer> map =new HashMap<String,Integer>();
        try{
            PropertiesConfiguration config = new PropertiesConfiguration(GlobalResource.ENV_PROPS.get("resource.properties"));
            Iterator<String> iter = config.getKeys();
            while(iter.hasNext()){
                String key = iter.next();
                int value = config.getInt(key);
                map.put(key,value);
            }
        }catch(Exception e){
            throw new Error("load resource error");
        }
        System.out.println(map);
    }

    @Test
    public void testOutQueue() throws Exception {
        InstanceDO inst = new InstanceDO();
        inst.setInstanceId("1001");
        inst.setTaskId(1001);
        inst.setDatabaseSrc("hive");
        inst.setType(2);
        ResourceManager2.inQueue(inst);
    }

    @Test
    public void testGenerateProcess() throws Exception{
        InstanceDO inst = new InstanceDO();
        inst.setTaskObj("d:/55.bat");
        inst.setLogPath("/data/deploy/44.log");
        inst.setInstanceId("12345");
        ProcessUtils.executeCommand(inst);
    }

    @Test
    public void testDestoryProcess() throws Exception{
        ResourceManager2.destoryProcess("12345");
    }

    @Test
    public void testLionConfig() throws Exception{
        String resourceProp = GlobalResource.ENV_PROPS.get("resource.properties").replace("${deploy_home}",GlobalResource.DEPLOY_HOME);
        System.out.println(resourceProp);
      }
}
