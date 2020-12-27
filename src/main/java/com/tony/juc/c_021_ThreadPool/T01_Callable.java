package com.tony.juc.c_021_ThreadPool;

import java.util.concurrent.*;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 20:18
 * Description: Callable有返回值的Runnable
 *
 */
public class T01_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c);//异步的

        System.out.println(future.get());//阻塞的

        service.shutdown();

    }
}
