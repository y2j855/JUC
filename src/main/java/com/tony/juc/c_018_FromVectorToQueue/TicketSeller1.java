package com.tony.juc.c_018_FromVectorToQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 10:33
 * Description: 容器多线程问题
 *
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 *
 * 程序产生的问题
 * 应为tickets是不支持多线程的，所以有可能出现最后一张票多个线程拿到，返回null。
 */
public class TicketSeller1 {
    static List<String> tickets = new LinkedList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (tickets.size() > 0){
                    System.out.println("销售了---" + tickets.remove(0));
                }
            }).start();
        }
    }
}
