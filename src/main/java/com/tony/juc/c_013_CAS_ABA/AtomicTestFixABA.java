package com.tony.juc.c_013_CAS_ABA;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 21:04
 * Description: CAS中ABA问题
 * 使用AtomicStampedReference解决ABA问题，添加版本号
 */
public class AtomicTestFixABA {
    private static AtomicInteger index = new AtomicInteger(10);
    static AtomicStampedReference<Integer> stampRef =
            new AtomicStampedReference<>(10, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            int stamp = stampRef.getStamp();
            System.out.println(Thread.currentThread().getName()
                    + " 第一次版本号: " + stamp);
            stampRef.compareAndSet(10, 11, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()
                    + " 第二次版本号: " + stampRef.getStamp());
            stampRef.compareAndSet(11, 10, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()
                    + " 第三次版本号: " + stampRef.getStamp());
        }, "张三").start();

        new Thread(()->{
            try {
                int stamp = stampRef.getStamp();
                System.out.println(Thread.currentThread().getName()
                        + " 第一次版本号: " + stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = stampRef.compareAndSet(10,12,
                        stampRef.getStamp(),stampRef.getStamp() + 1);
                System.out.println(Thread.currentThread().getName()
                +" 修改是否成功: " + isSuccess + " 当前版本: " + stampRef.getStamp());
                System.out.println(Thread.currentThread().getName()
                +" 当前实际值: " + stampRef.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"李四").start();
    }
}
