package com.tony.juc.c_020_interview_A1B2C3;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 10:25
 * Description: 使用TransferQueue完成
 * 2个线程，要求交替打印A1B2C3...Z26
 */
public class T10_TransferQueue {
    static LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 1; i <= 26; i++) {
                try {
                    System.out.println(queue.take());
                    queue.transfer(String.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            for (int i = 65; i < 91; i++) {
                char c = (char) (i);
                try {
                    queue.transfer(String.valueOf(c));
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();
    }
}
