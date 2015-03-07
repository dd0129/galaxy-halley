package com.dianping.data.warehouse.worker.utils;
 
import java.io.IOException;
import java.net.InetAddress;
 
public class InetAddressTest {
    public static void main(String[] args) throws IOException {
       InetAddress addr = InetAddress.getLocalHost();//获取本机ip
       System.out.println("local host : "+addr);    
      
       //获取指定服务的一个主机IP
       addr = InetAddress.getByName("google.com");
       System.out.println("google : " + addr);      
      
       //获取指定服务的所有主机IP
       InetAddress[] addrs = InetAddress.getAllByName("google.com");
       for(int i = 0 ;i < addrs.length ;i++)
           System.out.println("google : "+addrs[i] + "  number : " + i);     
      
       //获取远程主机可达性
       System.out.println(InetAddress.getByName("localhost").isReachable(1000));   
    }
}