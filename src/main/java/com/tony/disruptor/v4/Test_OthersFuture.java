package com.tony.disruptor.v4;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.*;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 20:47
 * Description:
 * 其他特性
 */
public class Test_OthersFuture {
    private static int count;
    public static void handleEvent(LongEvent event,long sequence,boolean endOfBatch){
        count++;
        System.out.println(event);
    }
    public static void main(String[] args) {
        /**
         * disruptor其他特性
         * 1.ProducerType SINGLE/MULTI 一个线程，多个线程
         * 2.WaiteStrategy 等待策略共8种，看笔记。
         * 3.MultiConsumer 支持多个消费者
         * 4.ExceptionHandler 异常处理
         * */


        int bufferSize = 1 << 10;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, Executors.defaultThreadFactory(),
                ProducerType.MULTI, new BlockingWaitStrategy());

        disruptor.handleEventsWith(Test_OthersFuture::handleEvent);

        //可以添加多个consumer
//        disruptor.handleEventsWith(Test_ProducerType::handleEvent,Test_ProducerType::handleEvent);

        //异常处理
//        EventHandler h1 = (event,sequence,end)->{
//            System.out.println(event);
//            throw new Exception("消费者出异常了");
//        };
//
//        disruptor.handleEventsWith(h1);
//
//        disruptor.handleExceptionsFor(h1).with(new ExceptionHandler<LongEvent>() {
//            @Override
//            public void handleEventException(Throwable throwable, long l, LongEvent event) {
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void handleOnStartException(Throwable throwable) {
//                System.out.println("Exception Start to Handle!");
//            }
//
//            @Override
//            public void handleOnShutdownException(Throwable throwable) {
//                System.out.println("Exception Handle!");
//            }
//        });

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        final int threadCount = 50;
        CyclicBarrier barrier = new CyclicBarrier(threadCount);
        ExecutorService service = Executors.newCachedThreadPool();
        for (long i = 0; i < threadCount; i++) {
            final long threadNum = i;
            service.submit(()->{
                System.out.printf("Thread %s ready to start!\n",threadNum);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < 100; j++) {
                    ringBuffer.publishEvent((event,sequence)->{
                        event.setValue(threadNum);
                        System.out.println("生产了 " + threadNum);
                    });
                }
            });
        }

        service.shutdown();
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
