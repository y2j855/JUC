package com.tony.juc.c_000.threadstate;

/**
 * @author: Tony.Chen
 * Create Time : 2021/4/10 10:52
 * Description:join()方法：waiting状态 join(long)方法：Timed_waiting状态
 */
public class Test2 {
    public static void main(String[] args) {
        Thread11 t1 = new Thread11();
        Thread21 t2 = new Thread21();
        Thread31 t3 = new Thread31(); //t1需要持有t2,t3的引用，以便打印他们的状态。
        t1.setThread2(t2, t3); //t2需要持有t3的引用，以便t3能够在t2执行时加入(调用join()方法)
        t2.setTh3(t3);
        t1.start();
        t2.start();
    }
}

//Thread1负责打印所有线程的状态。
class Thread11 extends Thread {
    private Thread21 t2;
    private Thread31 t3;

    public void setThread2(Thread21 t2, Thread31 t3) {
        this.t2 = t2;
        this.t3 = t3;
    }

    @Override
    public void run() {
        System.out.println("进入t1线程");
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("t1 的状态： " + getState());
                System.out.println("t2 的状态： " + t2.getState());
                System.out.println("t3 的状态： " + t3.getState());
                System.out.println();
                //为了减少打印次数，所以t1每打印一次睡1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}


class Thread21 extends Thread {
    private Thread31 t3;

    public void setTh3(Thread31 t3) {
        this.t3 = t3;
    } //当进入t2线程以后马上启动t3线程并调用join()方法。

    @Override
    public void run() {
        System.out.println("进入t2线程，t3准备加入(调用join()方法)");
        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t2执行结束");
    }
}

class Thread31 extends Thread {
    @Override
    public void run() {
        System.out.println("进入t3线程，准备睡眠");
        //本来是想让t3线程做加法运算的，奈何电脑算太快了，所以改为睡眠。因为睡眠不释放锁，所以效果一样。
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t3线程结束");
    }
}

