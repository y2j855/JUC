package com.tony.juc.c_018_FromVectorToQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 10:41
 * Description: 使用Vector/Collections.synchronizedXXX解决多线程问题
 * 发现使用vector或者sync还是会出现问题
 * 原因是因为：size，remove都是原子性的
 * 但是方法整体不是原子性的，如果线程1就入size，线程2已经remove了
 * 此时线程1在remove就会报错。
 *
 */
public class TicketSeller2 {
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (tickets.size() > 0){
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("销售了---" + tickets.remove(0));
                }
            }).start();
        }
    }
}
