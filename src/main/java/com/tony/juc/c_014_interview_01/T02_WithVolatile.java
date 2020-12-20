package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * @author: Tony.Chen
 * Create Time : 2020/12/20 11:59
 * Description: 添加volatile 也不能解决
 *
 * 原因
 * 因为List是引用类型，虽然list是可见性的了，但是我们修改的是list里边的值
 * 对于volatile还是不能可见。
 * 容器修改为同步容器还是不行，所以volatile慎用！
 *
 * volatile使用建议
 * 1.没有把握尽量不要使用volatile
 * 2.尽量修饰简单类型，不要修饰引用类型
 *
 * 奇怪现象:加了volatile不加同步容器也能完成功能。(不管，理解思想就行)
 *
 * 给lists添加volatile之后，t2能够接到通知，但是t2线程的死循环很浪费cpu，如果不用死循环，
 * 而且，如果在if和break之间被别的线程打断，得到的结果也不精确，用生产消费模式实现。
 */
public class T02_WithVolatile {
//    private volatile List<String> list = new ArrayList<>();
    private volatile List<String> list = Collections.synchronizedList(new ArrayList<>());

    public void add(String string) {
        list.add(string);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T02_WithVolatile t = new T02_WithVolatile();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                t.add("string " + i);
                System.out.println("string " + i);
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (t.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 结束");
        }, "t2").start();
    }
}
