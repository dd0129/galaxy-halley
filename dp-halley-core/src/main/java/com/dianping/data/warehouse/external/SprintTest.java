package com.dianping.data.warehouse.external;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongdi.tang on 14-5-5.
 */
public class SprintTest {

    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext
                ("classpath:test-spring-applicationcontext.xml");
        context.start();
        System.out.println("test");
    }
}
