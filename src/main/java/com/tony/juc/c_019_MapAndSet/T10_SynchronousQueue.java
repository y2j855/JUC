package com.tony.juc.c_019_MapAndSet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 16:54
 * Description: 不存储元素的阻塞队列,它的容量为0
 * synchronous总结
 * 1.是一个阻塞队列，其中每个put必须等待一个take，反之亦然。
 * 2.同步队列没有任何内部容量，甚至连一个队列的容量都没有。
 * 3.不能进行peek操作，因为仅在试图要取得元素时，该元素才存在，除非另一个线程试图移除某个元素，
 * 否则也不能（使用任何方法）添加元素，也不能迭代队列，因为其中没有元素可用于迭代。
 * 4.如果没有消费者，生产者就一直等待消费者拿数据，一直阻塞等待，直到消费者拿了数据，生产者才会结束阻塞。
 * 类似于生产者将数据递给消费者手里。
 * 5.支持公平锁非公平锁的切换。
 */
public class T10_SynchronousQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        //阻塞等待消费者消费
        strs.put("aaa");
//        strs.put("bbb");
//        strs.add("aaa");
        System.out.println(strs.size());
    }
}
