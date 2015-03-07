package com.dianping.data.warehouse.concurrency;

import java.util.concurrent.*;

public class Timeout implements Callable<String> {

    public void longMethod() {
        for(int i=0; i< Integer.MAX_VALUE; i++) {
            //System.out.println("a");
        }
    }

    @Override
    public String call() throws Exception {
        StringBuilder builder = new StringBuilder();
        try{
            for(int i=0;i<Integer.MAX_VALUE;++i){
                builder.append("a");
                Thread.sleep(100);
            }
        }catch(InterruptedException e){
            System.out.println("Thread was interrupted");
        }
        return builder.toString();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> futureResult = null;
        try {
           futureResult = service.submit(new Timeout());
           String result =  futureResult.get(1000, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            futureResult.cancel(true);
        }
    }


}