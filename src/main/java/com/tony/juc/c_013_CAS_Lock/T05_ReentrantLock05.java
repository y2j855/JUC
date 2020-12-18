package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 12:12
 * Description: ReentrantLock 可以指定为公平锁
 * 公平锁：进来的线程先检查等待队列里是否已有等待的线程，
 * 如果有它就进等待队列，如果没有直接申请锁。
 * 公平锁不是说完全的公平，还是要看等待队列有哪个线程就执行哪个。
 *
 * sync只有不公平锁
 */
public class T05_ReentrantLock05 extends Thread{
    //true 表示公平锁
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "获得锁");
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T05_ReentrantLock05 rl = new T05_ReentrantLock05();
        new Thread(rl::run).start();
        new Thread(rl::run).start();
    }
}
