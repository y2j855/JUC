package com.tony.juc.c_000.threadstate;

import java.util.Scanner;

/**
 * @author: Tony.Chen
 * Create Time : 2021/4/10 11:06
 * Description:IO阻塞操作:runnable状态
 */
public class Test4 {
    public static void main(String[] args) {
        Thread14 t1 = new Thread14();
        Thread24 t2 = new Thread24();
        t1.setThread2(t2);
        t1.start();
        t2.start();
    }
}

//Thread1负责打印所有线程的状态。
class Thread14 extends Thread {
    private Thread24 t2;

    public void setThread2(Thread24 t2) {
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("进入t1线程结束");
    }
}

class Thread24 extends Thread {
    @Override
    public void run() {
        System.out.println("进入t2线程"); //让线程进入I/O
        System.out.println("请输入数据：");
        Scanner scan = new Scanner(System.in);
        String read = scan.nextLine();
        System.out.println("您输入的数据为：" + read);
        System.out.println("t2线程结束");
    }
}
