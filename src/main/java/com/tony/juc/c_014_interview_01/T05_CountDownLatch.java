package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 15:20
 * Description: Latch(门闩) 来实现wait notify
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用await和countdown方法替代wait和notify
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 *
 * 如果要做到等于5,t2先输出然后t1再运行就用2个门闩。
 */
public class T05_CountDownLatch {
    private List list = new ArrayList();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        T05_CountDownLatch t = new T05_CountDownLatch();

        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        new Thread(()->{
            System.out.println("t2启动");
            if(t.size() != 5){
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch2.countDown();
            }
            System.out.println("t2结束");
        },"t2").start();

        new Thread(()->{
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add " + i);
                if(t.size()==5){
                    latch1.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();
    }
}
