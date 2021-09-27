package com.tony.juc.c_000.threadstate;

/**
 * @author: Tony.Chen
 * Create Time : 2021/4/10 11:03
 * Description:suspend()方法：线程挂起，runnable状态，并且它不会释放锁。
 */
public class Test3 {
    public static void main(String[] args) {
        Thread13 t1 = new Thread13();
        Thread23 t2 = new Thread23();
        //t1需要持有t2,以便打印状态，和控制它恢复运行。
        t1.setThread2(t2);
        t1.start();
        t2.start();
    }
}

//Thread1负责打印所有线程的状态。
class Thread13 extends Thread {
    private Thread23 t2;

    public void setThread2(Thread23 t2) {
        this.t2 = t2;
    }

    @Override
    public void run() {
        System.out.println("进入t1线程");
        for (int i = 0; i < 6; i++) {
            try {
                System.out.println("t1 的状态： " + getState());
                System.out.println("t2 的状态： " + t2.getState());
                System.out.println();
                if (i == 3) {
                    //恢复t2的运行。
                    t2.resume();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Thread23 extends Thread {
    @Override
    public void run() {
        System.out.println("进入t2线程，挂起");
        //将线程挂起。让t1来控制它的恢复运行。
        suspend();
        System.out.println("t2已经恢复运行");
        System.out.println("t2正在打印1");
        System.out.println("t2正在打印2");
        System.out.println("t2正在打印3");
        System.out.println("t2线程结束");
    }
}
