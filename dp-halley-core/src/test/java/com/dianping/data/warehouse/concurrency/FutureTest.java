package com.dianping.data.warehouse.concurrency;

import java.util.concurrent.*;

/**
 * Created by hongdi.tang on 2014/10/11.
 */
public class FutureTest {
    private ExecutorService executor ;

    public void test(){
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };
        FutureTask task = new FutureTask(callable);
        Future future = executor.submit(task);
        CompletionService service = new ExecutorCompletionService(executor);
    }
}
