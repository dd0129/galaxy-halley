package com.dianping.data.warehouse.worker.model;



import com.dianping.data.warehouse.halley.domain.PublishFileDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongdi.tang on 14-6-19.
 */
public class PublishResult {
    public enum ResultEnum{
        alreadyFailure(-2),failure(-1),success(1),wait(0);

        public int getCode() {
            return code;
        }

        private int code;
        private ResultEnum(int code){
            this.code = code;
        }
    }

    private static List<String> WORKER_NODE_IPS = new ArrayList<String>();

    static {
        WORKER_NODE_IPS.add("10.128.38.110");
    }

    private static Map<String,List<PublishFileDO>> resultMap =
            new ConcurrentHashMap<String, List<PublishFileDO>>();

    private static Map<String,Boolean> result = new ConcurrentHashMap<String, Boolean>();


    public static int isPublished(PublishFileDO file){
        int code = putResult(file);
        if(code == ResultEnum.success.getCode()){
            if(containsIP(file) ){
                if(putResultMap(file)){
                    return ResultEnum.success.getCode();
                }else{
                    return ResultEnum.wait.getCode();
                }
            }else{
                file.setFlag(false);
                putResult(file);
                return ResultEnum.failure.getCode();
            }
        }else{
            return code;
        }
    }

    private static boolean containsIP(PublishFileDO file){
        for(String ip :WORKER_NODE_IPS){
            if(file.getHost().equals(ip)){
                return true;
            }
        }
        return false;
    }

    private static boolean putResultMap(PublishFileDO file){
        List workerList = null;
        if(resultMap.containsKey(file.getPublishId())){
            workerList = resultMap.get(file.getPublishId());
            workerList.add(file);
        }else{
            workerList = new ArrayList<PublishFileDO>();
            workerList.add(file);
            resultMap.put(file.getPublishId(),workerList);
        }
        return WORKER_NODE_IPS.size() == workerList.size();
    }

    private static int putResult(PublishFileDO file){
        if(result.containsKey(file.getPublishId())){
            boolean flag = result.get(file.getPublishId());
            if(!flag){
                return ResultEnum.alreadyFailure.getCode();
            }
            result.put(file.getPublishId(),file.isFlag());
        }else{
            result.put(file.getPublishId(), file.isFlag());
        }
        return file.isFlag() ? ResultEnum.success.getCode() : ResultEnum.failure.getCode();
    }
}
