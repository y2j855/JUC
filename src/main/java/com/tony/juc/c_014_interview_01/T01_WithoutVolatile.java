package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 错误原因
 * 1.ArrayList是不同步的，add和size没有加同步，或者使用同步容器。
 * 2.list是不可见的
 * @author: Tony.Chen
 * Create Time : 2020/12/20 11:28
 * Description:
 */
public class T01_WithoutVolatile {
    private /*volatile*/ List<String> list = new ArrayList<>();

    public void add(String string){
        list.add(string);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        T01_WithoutVolatile t = new T01_WithoutVolatile();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                t.add("string " + i);
                System.out.println("string " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            while (true){
                if(t.size()==5){
                    break;
                }
            }
            System.out.println("t2 结束");
        },"t2").start();
    }
}
