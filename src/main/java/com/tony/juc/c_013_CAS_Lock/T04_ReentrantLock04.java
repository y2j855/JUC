package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 11:43
 * Description: ReentrantLock lockInterrputibly
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrput方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断。
 */
public class T04_ReentrantLock04 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();

        Thread t2 = new Thread(()->{
            try {
                /**
                 * 它会响应interrupt()方法，并抛异常，下面的代码不执行了
                 */
                lock.lockInterruptibly();
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            }finally {
                /**
                 * 由于线程1长时间占用锁，所以当前线程无法获取锁，要加判断，否则抛异常
                 */
                if(lock.tryLock()) {
                    lock.unlock();
                }
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.interrupt();
    }
}
