package com.dianping.data.warehouse.halley.core;

import com.dianping.data.warehouse.halley.service.LogAnalyzerService;
import com.dianping.data.warehouse.halley.service.imple.LogAnalyzerServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongdi.tang on 2015/1/14.
 */
public class LogAnalyzer {
    public static void main(String[] args){
        if(args.length!=1){
            return;
        }
        String timeId = args[0];
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring-applicationcontext.xml"}
        ) ;
        LogAnalyzerService service =(LogAnalyzerServiceImpl)context.getBean("logAnalyzerService");
        service.logParse(timeId);
    }
}
