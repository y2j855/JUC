package com.tony.juc.c_021_ThreadPool;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 20:47
 * Description: ThreadPool:认识线程池
 * 了解线程池基本概念以及参数含义
 * 线程池主要维护2中集合：1.线程集合。2.任务集合(Runnable)。
 * 线程池参数含义
 * corePoolSize:核心线程数。线程池被初始化后，如果没有任务线程池还不存在线程，当有任务时先创建核心线程。
 * maximumPoolSize:最大线程数
 * keepAliveTime:线程存活时间，超过时间线程销毁。
 * TimeUnit:存活时间的单位
 * BlockingQueue<Runnable>:任务队列，当线程池没有可用线程时，将任务放到队列里。
 * ThreadFactory:线程工程。可以自定义
 * RejectedExecutionHandler:拒绝策略，jdk默认提供4中。但现实项目中一般都是根据业务自定义。
 */
public class T06_HelloThreadPool {
    static class Task implements Runnable {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Task " + i);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 8; i++) {
            tpe.execute(new Task(i));
        }

        System.out.println(tpe.getQueue());
        tpe.execute(new Task(100));
        System.out.println(tpe.getQueue());
        tpe.shutdown();
    }
}
