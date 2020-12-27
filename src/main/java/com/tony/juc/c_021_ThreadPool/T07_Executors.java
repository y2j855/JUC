package com.tony.juc.c_021_ThreadPool;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 21:01
 * Description: Executors工厂类
 * 提供了一些静态工厂，生成一些常用的线程池。
 * 大厂不允许直接使用Executors创建线程池
 * 它的弊端
 * 1.FixedThreadPool和SingleThreadPool
 * 允许的请求队列长度为Integer.MAX_VALUE,可能会堆积大量的请求，从而导致OOM。
 * 2.CachedThreadPool
 * 允许的创建线程数量为Integer.MAX_VALUE,可能会创建大量的线程，从而导致OOM。
 */
public class T07_Executors {
    public static void main(String[] args) {
    }
}
