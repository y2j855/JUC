package com.tony.juc.c_019_MapAndSet;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 16:11
 * Description: 排序的队列，就是小根堆，数据结构算法里边有使用过。
 */
public class T07_PriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<String> strs = new PriorityQueue<>();

        strs.add("c");
        strs.add("e");
        strs.add("a");
        strs.add("d");
        strs.add("z");

        for (int i = 0; i < 5; i++) {
            System.out.println(strs.poll());
        }
    }
}
