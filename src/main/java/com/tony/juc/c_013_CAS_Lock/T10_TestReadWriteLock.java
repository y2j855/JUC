package com.tony.juc.c_013_CAS_Lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/19 09:06
 * Description: ReadWriteLock 读写锁，脏读问题。
 * ReadWriteLock   能够提高的读写锁的性能
 * 分成2个锁
 * 共享锁  所有读的线程共享，写的线程阻塞的
 * 排他锁  只有当前写线程使用，其他阻塞等待
 *
 * 使用场景：当需要大部分读数据，少部分写数据，能够提高效率。
 */
public class T10_TestReadWriteLock {
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock){
        lock.lock();
        try {
            Thread.sleep(1000);
            System.out.println("read over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock,int v){
        lock.lock();
        try {
            Thread.sleep(1000);
            value = v;
            System.out.println("write over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
//        Runnable readR = ()->read(lock);
//        Runnable writeR = ()->write(lock,new Random().nextInt());
        Runnable readR = ()->read(readLock);
        Runnable writeR = ()->write(writeLock,new Random().nextInt());

        for (int i = 0; i < 18; i++) {
            new Thread(readR).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(writeR).start();
        }
    }

}
