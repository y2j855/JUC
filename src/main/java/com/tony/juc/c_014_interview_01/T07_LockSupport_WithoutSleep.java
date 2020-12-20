package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 15:29
 * Description: LockSupport 实现wait notify
 * 如果想size==5，先输出t2,然后在让t1运行，也需要LockSupport两次
 */
public class T07_LockSupport_WithoutSleep {
    private List list = new ArrayList();

    static Thread t1 = null;
    static Thread t2 = null;

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T07_LockSupport_WithoutSleep t = new T07_LockSupport_WithoutSleep();

        t2 = new Thread(() -> {
            System.out.println("t2启动");
            LockSupport.park();
            System.out.println("t2结束");
            LockSupport.unpark(t1);
        }, "t2");


        t1 = new Thread(() -> {
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add " + i);
                if (t.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        }, "t1");

        t2.start();
        t1.start();
    }
}
