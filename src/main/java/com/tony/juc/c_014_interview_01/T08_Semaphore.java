package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 16:46
 * Description:
 */
public class T08_Semaphore {
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
        T08_Semaphore t = new T08_Semaphore();
        Semaphore s = new Semaphore(1);
        t1 = new Thread(() -> {
            try {
                s.acquire();
                System.out.println("t1启动");
                for (int i = 0; i < 5; i++) {
                    t.add(new Object());
                    System.out.println("add " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }

            try {
                t2.start();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                s.acquire();
                for (int i = 5; i < 10; i++) {
                    System.out.println("add " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }

        }, "t1");

        t2 = new Thread(() -> {
            System.out.println("t2启动");
            try {
                s.acquire();
                System.out.println("t2结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }
        }, "t2");

        t1.start();
    }
}
