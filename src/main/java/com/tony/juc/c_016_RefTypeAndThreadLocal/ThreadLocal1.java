package com.tony.juc.c_016_RefTypeAndThreadLocal;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 20:06
 * Description: ThreadLocal
 * 不使用ThreadLocal 线程2修改p，线程1会受影响
 */
public class ThreadLocal1 {
    static Person p = new Person();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(p.name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "lisi";
        }).start();
    }

}

class Person {
    String name = "zhangsan";
}
