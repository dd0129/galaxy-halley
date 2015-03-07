package com.dianping.data.warehouse.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by hongdi.tang on 14-4-14.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        String url = "jdbc:mysql://192.168.8.44:3306/DianPingDW2";
        String user = "DianPingDW2";
        String pass = "ertydfgh";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, pass);
        System.out.println(url);
        System.out.println(user);
        System.out.println(pass);
        System.out.println(conn);
    }
}
