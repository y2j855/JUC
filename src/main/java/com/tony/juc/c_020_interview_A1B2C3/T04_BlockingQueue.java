package com.tony.juc.c_020_interview_A1B2C3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 18:37
 * Description: 利用BlockingQueue实现交替打印
 * 通过2个blockingqueue来控制2个线程的交替打印
 * t1:先打印，然后往q1放值阻塞，等待t2拿q1的值。t2拿完值后，t1从q2拿值，此时要等待t2往q2放值。
 * t2:先从q1拿值，如果t1不放值，t2阻塞。然后打印，打印后再往q2放值。让t1解除阻塞
 * 这样就做到了2个线程的交替打印。
 * 只是为了使用blockingQueue来实现题目，现实项目不会这么用。
 */
public class T04_BlockingQueue {
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 1; i < 27; i++) {
                System.out.println(i);
                try {
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            for (int i = 65; i < 91; i++) {
                char c = (char) i;
                try {
                    q1.take();
                    System.out.println(c);
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
