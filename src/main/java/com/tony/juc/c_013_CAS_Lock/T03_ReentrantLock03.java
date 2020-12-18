package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 11:34
 * Description: ReentrantLock tryLock
 * 使用ReentrantLock可以进行"尝试锁定"tryLock,这样无锁锁定或者在指定时间内无法锁定,
 * 线程可以决定是否继续等待。
 */
public class T03_ReentrantLock03 {
    Lock lock = new ReentrantLock();

    void m1(){
        lock.lock();
        try{
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判定是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，
     * 所以要注意unlock的处理，必须放到finally中。
     */
    void m2(){
        boolean locked = false;
        try {
            locked = lock.tryLock(5,TimeUnit.SECONDS);
            System.out.println("m2 ..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(locked){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T03_ReentrantLock03 rl = new T03_ReentrantLock03();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }
}
