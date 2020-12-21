package com.tony.juc.c_015_AQS;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 09:42
 * Description: 学习AQS源码
 */
public class TestReentrantLock {
    private static int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        i++;
        lock.unlock();
    }
}
