package com.dianping.data.warehouse.ConnectTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongdi.tang on 14-4-14.
 */
public class Test {
    public static void main(String[] args) throws Exception{
//        String abc ="测试";
//        System.out.println(abc);
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.8.44:3306/DianPingDW2?", "DianPingDW2", "ertydfgh");
//        System.out.println(conn);
        final Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++){
                    map.put(i,i);
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++){
                    map.put(i,i);
                }

            }
        }).start();

        while(true){
            System.out.println(map.size());
        }
    }
}
