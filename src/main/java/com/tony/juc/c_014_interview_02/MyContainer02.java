package com.tony.juc.c_014_interview_02;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 15:55
 * Description:
 * 写一个固定容量的同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 利用ReentrantLock  Condition实现生产者消费者模式
 * Condition就是等待队列
 * 不用if用while的原因，是因为当线程wait了，再次唤醒需要再次检查条件。
 * 好处：它能够区分生产者和消费者，将它们放在不同的等待队列中。
 */
public class MyContainer02<T> {
    private int index = 0;
    private final int capacity = 10;
    private final LinkedList<T> lists = new LinkedList<>();

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public T get() {
        T value = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            value = lists.removeFirst();
            index--;
            //唤醒生产者
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return value;
    }

    public void put(T value) {
        try{
            lock.lock();
            while (lists.size() == capacity) {
                producer.await();
            }
            index++;
            lists.add(value);
            //唤醒消费者
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return index;
    }

    public static void main(String[] args) {
        //producer consumer
        MyContainer02<String> container = new MyContainer02();

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
