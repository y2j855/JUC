package com.tony.juc.c_014_interview_02;

import java.awt.image.ImageProducer;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 15:55
 * Description:
 * 写一个固定容量的同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 利用wait notify实现生产者消费者模式
 * while的原因，是因为当线程wait了，再次唤醒需要再次检查条件。
 */
public class MyContainer01<T> {
    private int index = 0;
    private final int capacity = 10;
    private final LinkedList<T> lists = new LinkedList<>();

    public synchronized T get() {
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T value = lists.removeFirst();
        index--;
        //通知生产者生产
        this.notifyAll();
        return value;
    }

    public synchronized void put(T value) {
        while (lists.size() == capacity) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(value);
        index++;
        //通知消费者消费
        this.notifyAll();
    }

    public int getCount() {
        return index;
    }

    public static void main(String[] args) {
        //producer consumer
        MyContainer01<String> container = new MyContainer01();

        //启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    container.put(Thread.currentThread().getName() + " " + j);
                }
            }, "producer" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + " " + container.get());
                }
            }, "consumer" + i).start();
        }
    }
}
