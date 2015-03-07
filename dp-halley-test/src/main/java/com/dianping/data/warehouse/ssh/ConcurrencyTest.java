package com.dianping.data.warehouse.ssh;

/**
 * Created by hongdi.tang on 2015/2/2.
 */
public class ConcurrencyTest {
    public volatile static int count =0;

    public static void increase(){
        synchronized (ConcurrencyTest.class){
            ++count;
        }
    }

    static class TestThread implements Runnable{
        @Override
        public void run() {
           // synchronized (TestThread.this){
                count++;
            //}
            //ConcurrencyTest.increase();
        }
    }

    public static void main(String[] args) throws Exception{
        for (int i=0;i<20;i++){
            new Thread(new ConcurrencyTest.TestThread()).start();
        }
        Thread.sleep(5000L);
        System.out.println(count);

    }
}
