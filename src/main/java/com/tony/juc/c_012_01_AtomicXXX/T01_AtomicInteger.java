package com.tony.juc.c_012_01_AtomicXXX;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 20:29
 * Description: AtomicInteger
 * 解决同样的问题的更高效的方法，使用AtomXXX类
 * AtomXXX类本身方法都是原子性的，使用CAS实现，号称不加锁。
 * 但不能保证多个方法连续调用是原子性的
 *
 */
public class T01_AtomicInteger {
    /*volatile*/ //int count = 0;

    AtomicInteger count = new AtomicInteger(0);

    /*synchronized*/void m(){
        for (int i = 0; i < 10000; i++) {
            //是线程安全的，它是原子性的
            count.incrementAndGet();//count++
        }
    }

    public static void main(String[] args) {
        T01_AtomicInteger t = new T01_AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m,"thread-" + i));
        }

        threads.forEach((o)->o.start());
        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }
}
