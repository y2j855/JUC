package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 15:29
 * Description: LockSupport 实现wait notify
 * 如果想size==5，先输出t2,然后在让t1运行，也需要LockSupport两次
 */
public class T06_LockSupport {
    private List list = new ArrayList();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        T06_LockSupport t = new T06_LockSupport();

        Thread t2 = new Thread(()->{
            System.out.println("t2启动");
            if(t.size() != 5){
                LockSupport.park();
            }
            System.out.println("t2结束");
        },"t2");

        t2.start();

        new Thread(()->{
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add " + i);
                if(t.size()==5){
                    LockSupport.unpark(t2);
                }
            }
        },"t1").start();


    }
}
