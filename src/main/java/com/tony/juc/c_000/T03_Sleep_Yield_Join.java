package com.tony.juc.c_000;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/16 21:09
 * Description:
 * sleep
 * yield
 * join
 */
public class T03_Sleep_Yield_Join {
    public static void main(String[] args) {
//        testSleep();
        testYield();
//        testJoin();
    }

    /**
     * 当前运行的线程停止运行，让加入的线程继续运行
     */
    private static void testJoin() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                System.out.println("AA" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

    /**
     * 让出一下cpu
     * 将当前运行的线程退出到等待状态，有可能出现刚退出的线程又被cpu放到运行状态。
     */
    private static void testYield() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
                if (i % 10 == 0) {
                    Thread.yield();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                if (i % 10 == 0) {
                    Thread.yield();
                }
                System.out.println("----------------B" + i);
            }
        }).start();
    }

    /**
     * 当前运行的线程进入睡眠状态
     */
    private static void testSleep() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
