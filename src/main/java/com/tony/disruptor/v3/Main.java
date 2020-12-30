package com.tony.disruptor.v3;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 19:36
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 使用lambdaAPI
         * 慎用，lambda会创建大量的小对象，尤其循环中使用lambda容易导致内存溢出。
         * 通过lambda省略定义对象
         * 不用在定义EventFactory,不用在定义EventHandler,不用在定义producer
         * */


        //must be power of 2,方便位运算
        int ringBufferSize = 1 << 2;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, ringBufferSize, Executors.defaultThreadFactory());

        disruptor.handleEventsWith((event,sequence,endOfBatch)-> System.out.println(event.getValue()));

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        System.out.println(ringBuffer.getBufferSize());


        ByteBuffer buffer = ByteBuffer.allocate(8);

        for (long i = 0; i < 10; i++) {
            buffer.putLong(0,i);
            ringBuffer.publishEvent((event,sequence,bb) ->event.setValue(bb.getLong(0)),buffer);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        disruptor.shutdown();

    }
}
