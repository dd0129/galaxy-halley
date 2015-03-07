package com.dianping.data.warehouse.utils;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.domain.ExceptionAlertDO;
import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.resource.ResourceManager2;
import com.dianping.data.warehouse.utils.Utilities.StreamWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.*;

/**
 * Created by adima on 14-3-24.
 */
public class ProcessUtils {

    @Resource(name = "exceptionService")
    private static ExceptionAnalyze exceptionAnalyze;

    private static Logger logger = LoggerFactory.getLogger(ProcessUtils.class);

    public static Integer executeWormholeCommand(InstanceDO inst) {
        FileOutputStream outStream = null;
        FileOutputStream snapStream = null;
        BufferedReader br = null;
        String cmdLine = StringUtils.join(new String[]{inst.getTaskObj(), inst.getPara1(), inst.getPara2(), inst.getPara3()}, " ");
        logger.warn("cmdline := " + cmdLine);
        try {
            Process process = Runtime.getRuntime().exec(cmdLine);
            ResourceManager2.addProcess(inst.getInstanceId(),process);
            File outputFile = new File(inst.getLogPath());
            File snapshotOutputFile = new File(StringUtils.join(new String[]{inst.getLogPath(), "snapshot"}, "."));
            outStream = new FileOutputStream(outputFile, true);
            snapStream = new FileOutputStream(snapshotOutputFile, false);

            StreamWriter errPrinter = new StreamWriter(process.getErrorStream(), outputFile, snapshotOutputFile);
            errPrinter.start();

            br = null;
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String line = null;

            String rtnCodeStr = null;

            while ((line = br.readLine()) != null) {
                outStream.write((line + "\r\n").getBytes());
                snapStream.write((line + "\r\n").getBytes());
                rtnCodeStr = line;
            }
            outStream.close();
            snapStream.close();
            br.close();
            outStream = null;
            snapStream = null;
            br = null;
            process.waitFor();
            return Integer.valueOf(StringUtils.substringAfter(rtnCodeStr, CoreConst.regex).trim());
        } catch (Exception e) {
            logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") execute wormhole command error", e);
            return CoreConst.INTERNAL_EXECUTE_ERROR;
        } finally {
            ResourceManager2.removeProcess(inst.getInstanceId());
            StreamWriter.cleanup(outStream, snapStream, br);
        }
    }

    public static Integer executeCommand(InstanceDO inst) {
        String para1 = StringUtils.replace(inst.getPara1(), "${runtime.recallNum}", String.valueOf(inst.getRecallNum()));
        String para2 = StringUtils.replace(inst.getPara2(), "${runtime.recallNum}", String.valueOf(inst.getRecallNum()));
        String para3 = StringUtils.replace(inst.getPara3(), "${runtime.recallNum}", String.valueOf(inst.getRecallNum()));
        String cmdLine = StringUtils.join(new String[]{inst.getTaskObj(), para1, para2, para3}, " ");
        logger.warn("cmdline := " + cmdLine);
        try {
            Process process = Runtime.getRuntime().exec(cmdLine);
            ResourceManager2.addProcess(inst.getInstanceId(), process);
            File outputFile = new File(inst.getLogPath());
            File snapshotOutputFile = new File(StringUtils.join(new String[]{inst.getLogPath(), "snapshot"}, "."));
            StreamWriter outPrinter = new StreamWriter(process.getInputStream(), outputFile, snapshotOutputFile);
            StreamWriter errPrinter = new StreamWriter(process.getErrorStream(), outputFile, snapshotOutputFile);
            outPrinter.start();
            errPrinter.start();
            return process.waitFor();
        } catch (Exception e) {
            logger.error(inst.getInstanceId() + "(" + inst.getTaskName() + ") execute command error", e);
            return CoreConst.INTERNAL_EXECUTE_ERROR;
        } finally {
            ResourceManager2.removeProcess(inst.getInstanceId());
            logger.info(inst.getInstanceId() + "(" + inst.getTaskName() + ") removed from processMap");
        }
    }

    private static boolean containCode(Integer code, String codes) {
        if (StringUtils.isBlank(codes)) {
            throw new NullPointerException("codes is null");
        }
        for (String tmp : codes.split(";")) {
            if (code == Integer.valueOf(tmp).intValue()) {
                return true;
            }
        }
        return false;
    }

    private static int getExceptionCode(File file, String productName)
    {
        FileInputStream is;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("log parse error :",e);
            return CoreConst.WORMHOLE_UNKNOWN_EXCEPTION;
        }
        ExceptionAlertDO alert = exceptionAnalyze.analyze(is, productName);
        if (alert != null)
            return alert.getId();
        return CoreConst.WORMHOLE_UNKNOWN_EXCEPTION;
    }

}
