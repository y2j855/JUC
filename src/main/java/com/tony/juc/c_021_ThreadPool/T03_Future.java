package com.tony.juc.c_021_ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 20:26
 * Description: Future 封装Callable的返回值
 * 一般配合线程池使用
 * FutureTask更加好用灵活。
 * 因为它即是一个Future又是一个Task(Runnable)
 */
public class T03_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });

        new Thread(task).start();
        System.out.println(task.get()); //阻塞的
    }
}
