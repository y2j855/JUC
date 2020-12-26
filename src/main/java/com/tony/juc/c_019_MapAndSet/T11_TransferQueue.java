package com.tony.juc.c_019_MapAndSet;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 18:14
 * Description: SynchronousQueue升级版
 * 区别
 * LinkedTransferQueue可以使用put,tryTransfer,transfer添加多个数据而不用等别的线程来获取。
 * tryTransfer和transfer与put不同的是，tryTransfer和transfer可以检测是否有线程在等待获取数据，
 * 如果检测到就立即发送新增的数据给这个线程获取而不用放入队列。
 */
public class T11_TransferQueue {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        strs.transfer("aaa");   //如果没有消费者会阻塞
        strs.tryTransfer("bbb",1, TimeUnit.SECONDS);
        strs.put("ccc");    //如果没有消费者不会阻塞

        System.out.println(strs.size());


//        strs.put("aaa");
    }
}
