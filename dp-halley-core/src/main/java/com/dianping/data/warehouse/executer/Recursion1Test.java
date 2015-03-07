package com.dianping.data.warehouse.executer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by hongdi.tang on 14-4-24.
 */
public class Recursion1Test {

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    private Set<String> set = new HashSet<String>();

    public void RecursionTest() throws Exception{
        Connection ret = null;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://10.1.1.220:3306/DianPingDW?autoReconnect=true&amp;autoReconnectForPools=true&amp;useUnicode=true&amp;characterEncoding=utf-8";
        String user = "DianPingDW";
        String pass = "Diand32adjl3dvDW";
        ret = DriverManager.getConnection(url, user, pass);

        String sql = "select * From etl_task_status where time_id='2014-11-18'";
        String relasql = "select * From etl_taskrela_status where time_stamp>'2014-11-17 21:00:00' and time_stamp<'2014-11-18 23:00:00'";
        Statement stmt = ret.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        Map<String,String> list = new HashMap<String, String>();
        while(result.next()){
            String instanceId = result.getString("task_status_Id");
            list.put(instanceId,null);
        }

        ResultSet result1 = stmt.executeQuery(relasql);

        Map<String,List<String>> map = new HashMap<String,List<String>>();
        while(result1.next()){
            String relaId = result1.getString("pre_sts_id");
            String instanceId = result1.getString("task_status_Id");
            List<String> list1 = null;
            if(map.containsKey(relaId)){
                list1 = map.get(relaId);
                list1.add(instanceId);
            }else{
                list1= new ArrayList<String>();
                list1.add(instanceId);
                map.put(relaId,list1);
            }
        }

//        String startId = "103712014042400";
//        List<String> list2 = map.get(startId);
//
//        List<String> cascadeList = new ArrayList<String>();
//        cascadeList.add("103712014042400");
        this.execute("115082014111800",map);
        this.execute("115092014111800",map);
        this.execute("115122014111800",map);
        this.execute("115132014111800",map);
        this.execute("115182014111800",map);
        this.execute("115192014111800",map);
        this.execute("115212014111800",map);
        this.execute("115222014111800",map);
        this.execute("115362014111800",map);
        this.execute("115372014111800",map);
        this.execute("115722014111800",map);
        this.execute("116032014111800",map);
        this.execute("116042014111800",map);
        this.execute("116052014111800",map);
        this.execute("116062014111800",map);
        this.execute("116072014111800",map);
        this.execute("116082014111800",map);
        this.execute("116092014111800",map);
        this.execute("116102014111800",map);
        this.execute("116112014111800",map);
        this.execute("116122014111800",map);
        this.execute("116132014111800",map);
        this.execute("116142014111800",map);
        this.execute("117162014111800",map);
        this.execute("117172014111800",map);
        this.execute("117182014111800",map);
        this.execute("117192014111800",map);
        this.execute("117312014111800",map);
        this.execute("117322014111800",map);
        this.execute("117452014111800",map);
        this.execute("117462014111800",map);
        this.execute("117512014111800",map);
        this.execute("117522014111800",map);
        this.execute("117542014111800",map);
        this.execute("117552014111800",map);
        this.execute("117562014111800",map);
        this.execute("117572014111800",map);
        this.execute("117582014111800",map);
        this.execute("117592014111800",map);
        this.execute("117882014111800",map);
        this.execute("117892014111800",map);
        this.execute("117992014111800",map);
        this.execute("118042014111800",map);
        this.execute("118052014111800",map);
        this.execute("118062014111800",map);
        this.execute("118072014111800",map);
        this.execute("118942014111800",map);
        this.execute("118952014111800",map);
        this.execute("119002014111800",map);
        this.execute("119052014111800",map);
        this.execute("119062014111800",map);
        this.execute("119092014111800",map);
        this.execute("119102014111800",map);
        this.execute("119302014111800",map);
        this.execute("119692014111800",map);
        this.execute("119702014111800",map);
        this.execute("119882014111800",map);
        this.execute("119892014111800",map);
        this.execute("119912014111800",map);
        this.execute("119972014111800",map);
        this.execute("119982014111800",map);
        this.execute("123412014111800",map);
        this.execute("123422014111800",map);
        this.execute("123462014111800",map);
        this.execute("123692014111800",map);
        this.execute("123792014111800",map);
        this.execute("123802014111800",map);
        this.execute("123892014111800",map);
        this.execute("123902014111800",map);
        this.execute("124052014111800",map);
        this.execute("2091412014111800",map);
        this.execute("2091812014111800",map);
        this.execute("2091912014111800",map);
        this.execute("2092012014111800",map);
        this.execute("2092212014111800",map);
        this.execute("2093412014111800",map);
        this.execute("2093812014111800",map);
        this.execute("2094212014111800",map);
        this.execute("2094512014111800",map);
        this.execute("2095112014111800",map);
        this.execute("2095212014111800",map);
        this.execute("2095612014111800",map);
        this.execute("2095712014111800",map);
        this.execute("2095812014111800",map);
        this.execute("2095912014111800",map);
        this.execute("2096012014111800",map);
        this.execute("2096112014111800",map);
        this.execute("2096212014111800",map);
        this.execute("2096312014111800",map);
        this.execute("2096412014111800",map);
        this.execute("2096512014111800",map);
        this.execute("2102812014111800",map);
        this.execute("2103612014111800",map);
        this.execute("2103712014111800",map);
        this.execute("2103812014111800",map);
        this.execute("2103912014111800",map);
        this.execute("2105012014111800",map);
        this.execute("2106312014111800",map);
        this.execute("2106912014111800",map);
        this.execute("2108012014111800",map);
        this.execute("2109812014111800",map);
        this.execute("2116212014111800",map);
        this.execute("2116812014111800",map);
        this.execute("2117112014111800",map);
        this.execute("2119912014111800",map);
        this.execute("2120012014111800",map);
        this.execute("2120312014111800",map);
        this.execute("2120712014111800",map);
        this.execute("2120812014111800",map);
        this.execute("2122812014111800",map);
        this.execute("3024612014111800",map);
        this.execute("3025312014111800",map);
        this.execute("3025712014111800",map);
        this.execute("4047212014111800",map);
        this.execute("4047412014111800",map);
        this.execute("4051512014111800",map);
        this.execute("4053712014111800",map);
        this.execute("4054712014111800",map);
        this.execute("4055212014111800",map);
        this.execute("4056512014111800",map);
        this.execute("4056612014111800",map);
        this.execute("4056912014111800",map);
        this.execute("4057012014111800",map);
        this.execute("4057112014111800",map);
        this.execute("4057712014111800",map);
        this.execute("4057812014111800",map);
        this.execute("4058112014111800",map);
        this.execute("6020612014111800",map);
        this.execute("6021512014111800",map);

    }

    private void execute(String id,Map<String,List<String>> map){
        try{
            System.out.println(id);
            List<String> list2 = map.get(id);
            for(String s : list2){
                set.add(s);
                this.execute(s,map);
            }
        }catch(Exception e){
            //e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        Recursion1Test test = new Recursion1Test();
        test.RecursionTest();
        for(String s : test.getSet()){
            System.out.println(s);
        }

    }
}
