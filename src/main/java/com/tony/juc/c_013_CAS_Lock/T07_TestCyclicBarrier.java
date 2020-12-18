package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 15:03
 * Description: CyclicBarrier 栅栏 循环屏障
 */
public class T07_TestCyclicBarrier {
    public static void main(String[] args) {

//    CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满员，发车"));

//    CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
//        @Override
//        public void run() {
//            System.out.println("满员发车");
//        }
//    });

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }



}

