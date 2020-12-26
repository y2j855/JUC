package com.tony.juc.c_018_FromVectorToQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 10:54
 * Description: 保证方法整体是原子性的
 * 在方法中加入synchronized块
 * 有没有更好的解决办法，使用同步容器Queue
 */
public class TicketSeller3 {
    static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (tickets.size() > 0){
                    synchronized (tickets) {
                        if(tickets.size() <=0){
                            break;
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("销售了---" + tickets.remove(0));
                    }
                }
            }).start();
        }
    }
}
