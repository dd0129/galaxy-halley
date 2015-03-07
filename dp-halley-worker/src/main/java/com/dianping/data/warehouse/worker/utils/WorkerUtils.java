package com.dianping.data.warehouse.worker.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by hongdi.tang on 14-7-16.
 */
public class WorkerUtils {

    private static Logger logger = LoggerFactory.getLogger(WorkerUtils.class);

    public static String getHostIP() throws Exception {
        NetworkInterface ni = NetworkInterface.getByName("eth0");
        Enumeration<InetAddress> ias = ni.getInetAddresses();
        while(ias.hasMoreElements()) {
            InetAddress ia = ias.nextElement();
            if (ia instanceof Inet4Address){
                return ia.getHostAddress();
            }
        }
        throw new RuntimeException("can not get host address");
    }

    public static void main(String[] args) {
        try{
            System.out.println(WorkerUtils.getHostIP());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
