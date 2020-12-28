package com.tony.juc.c_021_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 20:44
 * Description: ExecutorService:完善了线程池生命周期相关的方法
 * 它继承自Executor
 * 线程池都是基于这个类来实现的
 */
public class T05_ExecutorService {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

    }
}
