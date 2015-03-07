package com.dianping.data.warehouse.worker.zk.dameon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongdi.tang on 14-7-1.
 */
public class WorkerDameon {

    private static Logger logger = LoggerFactory.getLogger(WorkerDameon.class);

    public static void main(String[] args){
        new ClassPathXmlApplicationContext("spring-applicationcontext.xml");
        while(true){
            try{
                Thread.sleep(10000);
                logger.info("process dead loop block");
            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }

        }
//        InputStreamReader ir = new InputStreamReader(System.in);
//        BufferedReader buf = new BufferedReader(ir);
//        try {
//            logger.info("退出请输入exit：");
//            String line;
//            while((line = buf.readLine())!=null){
//                System.out.println(line);
//                if(line.equalsIgnoreCase("exit")){
//                    System.exit(0);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("worker dameon process start fail", e);
//            System.exit(1);
//        }
    }
}
