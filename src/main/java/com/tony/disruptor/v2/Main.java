package com.tony.disruptor.v2;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tony.disruptor.v1.LongEvent;
import com.tony.disruptor.v1.LongEventFactory;
import com.tony.disruptor.v1.LongEventHandler;
import com.tony.disruptor.v1.LongEventProducer;

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
         * 1.创建EventFactory
         * 2.设置ringBuffer的大小，大小必须是2的N次幂，为了位运算。
         * 3.创建disruptor对象，指定线程工厂
         * 4.将EventHandler处理event对象放入disruptor
         * 5.启动disruptor
         * 6.获取ringbuffer，让生产者将消息放入ringbuffer
         * */

        LongEventFactory factory = new LongEventFactory();

        //must be power of 2,方便位运算
        int ringBufferSize = 1 << 2;

        Disruptor<LongEvent> disruptor = new Disruptor<com.tony.disruptor.v1.LongEvent>(factory, ringBufferSize, Executors.defaultThreadFactory());

        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        System.out.println(ringBuffer.getBufferSize());

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer buffer = ByteBuffer.allocate(8);

        for (long i = 0; i < 10; i++) {
            buffer.putLong(0,i);
            producer.onData(buffer);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        disruptor.shutdown();

    }
}
