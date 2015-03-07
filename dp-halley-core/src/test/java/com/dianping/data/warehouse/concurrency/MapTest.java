package com.dianping.data.warehouse.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongdi.tang on 2015/2/28.
 */
public class MapTest implements Runnable{
    public static Map<Integer,String> map = new ConcurrentHashMap<Integer, String>();

    public static void main(String[] args){
        //new Thread(new MapTest()).start();
        for(int i =0;i<100;i++){
            //synchronized (map){
                map.put(i,"ss"+i);
        }
        for(Map.Entry<Integer,String> entry : map.entrySet()){
            System.out.println(entry.getKey());
            map.remove(entry.getKey());
            System.out.println(entry.getKey());
        }
    }

    @Override
    public void run() {
        while(true){
            for(Map.Entry<Integer,String> entry : map.entrySet()){
                System.out.println("remove record "+entry.getKey());
                //synchronized (map){
                    map.remove(entry.getKey());
                //}
                try{
                    Thread.sleep(200L);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
