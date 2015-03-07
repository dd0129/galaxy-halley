package com.dianping.data.warehouse.validator;

import com.dianping.data.warehouse.common.CoreConst;
import com.dianping.data.warehouse.domain.TaskDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hongdi.tang on 14-3-26.
 */
public class TaskValidator {
    private static Logger logger = LoggerFactory.getLogger(TaskValidator.class);

    private static String[] validateOffsetType(String offsetType,String offset){
        String regex = "[D|M][\\d]{1,3}";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(offset);
        boolean flag = "offset".equals(offsetType) && matcher.matches();
        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag){
            rtn[1] = "offset "+offset+ " or offsetType "+ offsetType+" is illegal";
        }
        return rtn;
    }

    private static String[] validateWait(Integer ifWait,String waitCode){
        boolean flag = false;
        CoreConst.TASK_IFWAIT[] codes = CoreConst.TASK_IFWAIT.values();
        for (CoreConst.TASK_IFWAIT code : codes) {
            if (ifWait == code.getValue()) {
                flag = true;
            }
        }
        if(flag && ifWait == CoreConst.TASK_IFWAIT.WAITED.getValue()){
            flag = validateCodes(waitCode)[0].equals("1");
        }

        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag) {
            rtn[1] = "ifWait " + ifWait + " or waitCode " + waitCode + " is illegal";
        }
        return rtn;
    }

    private static String[] validateIfPre(Integer ifPre){
        boolean flag = false;
        CoreConst.TASK_IFPRE[] codes = CoreConst.TASK_IFPRE.values();
        for(int i =0;i<codes.length;i++){
            if(ifPre == codes[i].getValue()){
                flag = true;
            }
        }
        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag){
            rtn[1] = "ifPre "+ifPre +" is illegal";
        }
        return rtn;
    }


    private static String[] validateTaskType(Integer taskType){
        boolean flag = false;
        CoreConst.TASK_TYPE[] codes = CoreConst.TASK_TYPE.values();
        for(int i =0;i<codes.length;i++){
            if(taskType == codes[i].getValue()){
                flag = true;
            }
        }
        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag){
            rtn[1] = "taskType "+taskType +" is illegal";
        }
        return rtn;
    }

    private static String[] validateRecall(Integer ifRecall,Integer recallLimit){
        boolean flag = false;
        CoreConst.TASK_IFPRE[] codes = CoreConst.TASK_IFPRE.values();
        for (CoreConst.TASK_IFPRE code : codes) {
            if (ifRecall == code.getValue()) {
                flag = true;
            }
        }
        if(flag && ifRecall == CoreConst.TASK_IFRECALL.RECALL.getValue()){
            flag = recallLimit <= 30;
        }
        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag){
            rtn[1] = "ifRecall "+ifRecall +" and recallLimit "+recallLimit+" is illegal";
        }

        return rtn;
    }

    private static String[] validateCodes(String codes){
        String regex = "(\\d+;?)+";
        //String regex = "^\\d[[\\d]*[;]?]+";
        boolean flag = Pattern.matches(regex,codes);
        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag){
            rtn[1] = "codes "+codes +" is illegal";
        }
        return rtn;
    }

    public static String[] validateCycle(String cycle){
        boolean flag = false;
        CoreConst.TASK_CYCLE[] codes = CoreConst.TASK_CYCLE.values();
        for (CoreConst.TASK_CYCLE code : codes) {
            if (cycle.equals(code.toString())) {
                flag = true;
            }
        }
        String[] rtn = new String[2];
        rtn[0] = flag ? "1" : "0";
        if(!flag){
            rtn[1] = "cycle "+cycle +" is illegal";
        }
        return rtn;
    }

//    public static String[] validateKeyPropery(TaskDO task){
//        StringBuilder builder = new StringBuilder();
//        String[] rtn = validateCycle(task.getCycle());
//        if(rtn[1] != null){
//            builder.append(rtn[1]);
//        }
//        String msg = null;
//        if(!StringUtils.isBlank(builder.toString())){
//            msg = builder.toString();
//        }
//        boolean flag =  rtn[0].equals("1");
//        return null;
//    }

    public static String[] validateTask(TaskDO task){
        StringBuilder builder = new StringBuilder();
        String[] rtn1=null,rtn2=null,rtn3=null,rtn4=null,rtn5=null,rtn6=null;
        try{
            rtn1 = validateCodes(task.getSuccessCode());
            rtn2 = validateRecall(task.getIfRecall(),task.getRecallLimit());
            rtn3 = validateWait(task.getIfWait(),task.getWaitCode());
            //rtn4 = validateTaskType(task.getType());
            rtn5 = validateIfPre(task.getIfPre());
            rtn6 = validateOffsetType(task.getOffsetType(), task.getOffset());
        }catch(Exception e){
            logger.error("validate task error",e);
            return new String[]{"0",e.getMessage()};
        }


        if(rtn1[1] != null){
            builder.append(rtn1[1]);
        }
        if(rtn2[1] != null){
            builder.append(rtn2[1]);
        }
        if(rtn3[1] != null){
            builder.append(rtn3[1]);
        }
//        if(rtn4[1] != null){
//            builder.append(rtn4[1]);
//        }
        if(rtn5[1] != null){
            builder.append(rtn5[1]);
        }
        if(rtn6[1] != null){
            builder.append(rtn6[1]);
        }

        String msg = null;
        if(!StringUtils.isBlank(builder.toString())){
            msg = builder.toString();
        }

        boolean flag =  rtn1[0].equals("1") &&
                rtn2[0].equals("1") &&
                rtn3[0].equals("1") &&
                //rtn4[0].equals("1") &&
                rtn5[0].equals("1") &&
                rtn6[0].equals("1");
        return new String[]{flag?"1":"0",msg};
    }
}
