package com.tony.juc.c_009_volatile;

import com.tony.juc.c_001_synchronized.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 16:18
 * Description:
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * volatile不能保证原子性
 * 方法加synchronized
 */
public class T02_T {
    /*volatile*/ int count = 0;
    synchronized void m(){
        for (int i = 0; i < 10000; i++) {
            //不能保证原子性
            count++;
        }
    }

    public static void main(String[] args) {
        T02_T t = new T02_T();
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
