package com.tony.juc.c_018_FromVectorToQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 10:59
 * Description: 使用ConcurrentQueue提高并发性
 */
public class TicketSeller4 {
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (tickets.size() > 0){
                    String s = tickets.poll();
                    if(s == null) {
                        break;
                    }else {
                        System.out.println("销售了---" + s);
                    }


                }
            }).start();
        }
    }
}
