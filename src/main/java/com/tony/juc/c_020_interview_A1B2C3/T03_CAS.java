package com.tony.juc.c_020_interview_A1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 17:56
 * Description: 按照CAS的思路实现，实现多线程轻量级锁
 * 利用while来模拟自旋
 * 初始值会让t2进入自旋状态，当两个线程运行时，t1会解除t2的自旋，t2会解除t1的自旋
 * 从而实现交替打印的效果。
 * 现在是先输出数字再输出字母，如果想换顺序直接需改r的值就可以了。
 */
public class T03_CAS {
    enum ReadyToRun {T1,T2};

    static volatile ReadyToRun r = ReadyToRun.T1;   //当前准备运行的线程

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 1; i < 27; i++) {
                while (r == ReadyToRun.T2){ //模拟cas自旋
                }
                System.out.println(i);
                r = ReadyToRun.T2;
            }
        },"t1").start();

        new Thread(()->{
            for (int i = 65; i < 91; i++) {
                while (r == ReadyToRun.T1){
                }
                char c = (char) i;
                System.out.println(c);
                r = ReadyToRun.T1;
            }
        },"t2").start();
    }
}
