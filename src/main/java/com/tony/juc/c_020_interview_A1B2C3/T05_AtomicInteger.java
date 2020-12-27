package com.tony.juc.c_020_interview_A1B2C3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 19:00
 * Description: AtomicInteger实现交替打印
 * 实现逻辑与CAS很类似
 */
public class T05_AtomicInteger {
    static AtomicInteger number = new AtomicInteger(1);

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 1; i < 27; i++) {
                while (number.get() != 1){
                }
                System.out.println(i);
                number.set(2);
            }
        },"t1").start();

        new Thread(()->{
            for (int i = 65; i < 91; i++) {
                while (number.get() != 2){
                }
                char c = (char) i;
                System.out.println(c);
                number.set(1);
            }
        }).start();
    }
}
