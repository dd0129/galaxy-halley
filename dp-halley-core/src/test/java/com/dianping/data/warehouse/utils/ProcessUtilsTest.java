package com.dianping.data.warehouse.utils;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.resource.ResourceManager2;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongdi.tang on 14-4-18.
 */
public class ProcessUtilsTest {
    @org.junit.Test
    public void testExecuteWormholeCommand() throws Exception {
        String s = "fuck aseofijasopefj return code- 2112 ";
        Integer code = Integer.valueOf(StringUtils.substringAfter(s, CoreConst.regex).trim());
        System.out.println(code);
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final List<Process> list = new ArrayList<Process>();
        final InstanceDO inst = new InstanceDO();
        inst.setTaskObj("d:/55.bat");
        inst.setInstanceId("12345");
        inst.setLogPath("d:/data/55.log");

        Runnable r = new Runnable(){
            @Override
            public void run() {

                String para1 = StringUtils.replace(inst.getPara1(),"${runtime.recallNum}",String.valueOf(inst.getRecallNum()));
                String para2 = StringUtils.replace(inst.getPara2(),"${runtime.recallNum}",String.valueOf(inst.getRecallNum()));
                String para3 = StringUtils.replace(inst.getPara3(),"${runtime.recallNum}",String.valueOf(inst.getRecallNum()));
                String cmdLine = StringUtils.join(new String[]{inst.getTaskObj(),para1,para2,para3}," ");
                try{
                    Process process = Runtime.getRuntime().exec(cmdLine);
                    list.add(process);
                    ResourceManager2.addProcess(inst.getInstanceId(), process);
                    File outputFile = new File(inst.getLogPath());
                    File snapshotOutputFile = new File(StringUtils.join(new String[]{inst.getLogPath(), "snapshot"}, "."));
                    Utilities.StreamWriter outPrinter = new Utilities.StreamWriter(process.getInputStream(), outputFile,snapshotOutputFile);
                    Utilities.StreamWriter errPrinter = new Utilities.StreamWriter(process.getErrorStream(), outputFile,snapshotOutputFile);
                    outPrinter.start();
                    errPrinter.start();
                    process.waitFor();
                    while(true){
                        System.out.println("22222222222222");
                    }
                }catch(Exception e){
                }finally{
                    ResourceManager2.removeProcess(inst.getInstanceId());
                }
            }
        };

        Thread t =new Thread(r);
        t.start();


        while(true){
            System.out.println("111111");
        }
    }
}
