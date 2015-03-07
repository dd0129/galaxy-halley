package com.dianping.data.warehouse.halley.service.imple;

import com.dianping.data.warehouse.halley.dao.InstanceDAO;
import com.dianping.data.warehouse.halley.model.InstanceDO;
import com.dianping.data.warehouse.halley.model.TransformLogDO;
import com.dianping.data.warehouse.halley.service.LogAnalyzerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hongdi.tang on 2015/1/20.
 */
@Service("logAnalyzerService")
public class LogAnalyzerServiceImpl implements LogAnalyzerService {

    @Resource(name = "instanceDAO")
    private InstanceDAO instDAO;
    @Override
    public void logParse(String timeId) {
        List<InstanceDO> instList = instDAO.getInstances(timeId);
        System.out.println("parse log starts");
        for(InstanceDO inst : instList){
            File file = new File(inst.getLogPath());
            try{
                FileReader isr = new FileReader(file);
                BufferedReader br = new BufferedReader(isr);
                String line =null;
                TransformLogDO log = new TransformLogDO();
                while((line = br.readLine()) != null) {
                    String startTimeRegex = "Wormhole starts work at";
                    if (line.contains(startTimeRegex)) {
                        String startTime = StringUtils.substringAfter(line, ":").trim();
                        log.setStartTime(startTime);
                    }
                    String endTimeRegex = "Wormhole ends work at";
                    if (line.contains(endTimeRegex)) {
                        String endTime = StringUtils.substringAfter(line, ":").trim();
                        log.setEndTime(endTime);
                    }
                    String timeConstRegex = "Total time costs";
                    if (line.contains(timeConstRegex)) {
                        double timeCost = Double.valueOf(line.replace(timeConstRegex, "").replace(":", "").replace("s", "").trim());
                        log.setTimeCost(timeCost);
                    }
                    String avgByteSpeedRegex = "Average byte speed";
                    if (line.contains(avgByteSpeedRegex)) {
                        String avgByteSpeed = line.replace(avgByteSpeedRegex,"").replace(":","").trim();
                        log.setAvgByteSpeed(avgByteSpeed);
                    }
                    String avgLineSpeedRegex = "Average line speed";
                    if (line.contains(avgLineSpeedRegex)) {
                        double avgLineSpeed = Double.valueOf(line.replace(avgLineSpeedRegex, "").replace(":", "").replace("L/s", "").trim());
                        log.setAvgLineSpeed(avgLineSpeed);
                    }
                    String totalRecoredsRegex = "Total transferred records";
                    if (line.contains(totalRecoredsRegex)) {
                        double totalRecoreds = Double.valueOf(line.replace(totalRecoredsRegex,"").replace(":",""));
                        log.setTotalRecords(totalRecoreds);
                    }
                    String ipRegex = "process will execute on ";
                    if (line.contains(ipRegex)) {
                        String ip = line.replace(ipRegex,"").replaceAll(" after (\\d)+ seconds", "").trim();
                        log.setHost(ip);
                    }


                }
                log.setInstanceId(inst.getInstanceId());
                log.setTaskId(inst.getTaskId());
                log.setTimeId(inst.getTimeId());
                log.setLogPath(inst.getLogPath());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currTime = formatter.format(new Date());
                log.setTimestamp(currTime);
                instDAO.deleteLog(log.getInstanceId());
                instDAO.insertLog(log);
                System.out.println(inst.getInstanceId()+"("+inst.getTaskName()+") parse ends");
            }catch (Exception e){
                e.printStackTrace();
            }
            //
        }
    }

    public static void main(String[] args){
        String s = StringUtils.substringAfter("Wormhole starts work at : 2015-01-19 03:26:54", "Total time costs :");
        System.out.println("Wormhole starts work at : 2015-01-19 03:26:54".replaceAll("(\\w)", ""));
    }
}
