package com.tony.juc.c_019_MapAndSet;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 16:03
 * Description: 数组阻塞队列
 * 空间有限制的阻塞队列，需要设置大小
 */
public class T06_ArrayBlockingQueue {
    static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            strs.add("a" + i);
        }

//        strs.put("aaa"); //满了就会等待，程序阻塞
//        strs.add("aaa");   //满了会抛异常
//        strs.offer("aaa");//不会阻塞，不会抛异常
//        //可以设置尝试时间，如果过时间直接结束
        strs.offer("aaa",1, TimeUnit.SECONDS);

        System.out.println(strs);

    }
}
