package com.tony.disruptor.sample;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/31 15:10
 * Description:
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int bufferSize = 1<<10;
        ThreadFactory factory = Executors.defaultThreadFactory();
        Disruptor<Order> disruptor = new Disruptor<Order>(Order::new,bufferSize, factory,
                ProducerType.SINGLE,new BusySpinWaitStrategy());

        /**
         * 菱形操作
         * 使用disruptor创建消费者组c1,c2
         * 声明在c1，c2执行完再走C3
         * */
        EventHandlerGroup<Order> handlerGroup =
                disruptor.handleEventsWith(new DBHandler(),new ThirdHandler());
        handlerGroup.then(new BusinessHandler());

        //消费者的"顺序执行模式"
//        EventHandlerGroup<Order> handlerGroup =
//                disruptor.handleEventsWith(new DBHandler())
//                        .handleEventsWith(new ThirdHandler())
//                        .handleEventsWith(new BusinessHandler());


        disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);

        //生产者准备
        OrderPublisher publisher = new OrderPublisher(disruptor,latch);
        publisher.sendData();
        latch.await();
        disruptor.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("总耗时:" +(end-start));
    }
}
