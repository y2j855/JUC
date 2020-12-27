package com.tony.juc.c_020_interview_A1B2C3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 19:36
 * Description: 利用Lock + Condition实现2个线程交替打印
 * try catch不要放到循环里
 *
 * 也可以用2个Condition实现，因为就2个线程所以意义不大。
 */
public class T08_Lock_Condition {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(()->{
            lock.lock();
            try {
                for (int i = 1; i < 27; i++) {
                    System.out.println(i);
                    condition.await();
                    condition.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                for (int i = 65; i < 91; i++) {
                    char c = (char) i;
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        },"t2").start();
    }
}
