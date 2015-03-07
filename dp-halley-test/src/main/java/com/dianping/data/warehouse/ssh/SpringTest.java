package com.dianping.data.warehouse.ssh;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongdi.tang on 14-5-5.
 */
public class SpringTest {

    public void test(){
//        ApplicationContext context = new ClassPathXmlApplicationContext
//                ("classpath:test-spring-applicationcontext.xml","classpath:test-spring-applicationcontext.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test-spring-applicationcontext.xml");
        System.out.println("test");
    }

    public static void main(String[] args){
//        SpringTest test = new SpringTest();
//        test.test();
        System.out.println(System.getenv("home"));
    }
}
