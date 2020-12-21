package com.tony.juc.c_014_interview_02;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 21:22
 * Description:
 * 2个线程，要求交替打印A1B2C3...Z26
 */
public class UseLockSupport {
    static Thread t1 = null;
    static Thread t2 = null;
    public static void main(String[] args) {
        t1 = new Thread(()->{
            for (int i = 1; i <= 26; i++) {
                LockSupport.park();
                System.out.println(i);
                LockSupport.unpark(t2);
            }
        },"t1");

        t2 = new Thread(()->{
            for (int i = 65; i < 91; i++) {
                char c = (char) (i);
                System.out.println(c);
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
