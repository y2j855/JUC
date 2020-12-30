package com.tony.disruptor.v1;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 19:32
 * Description: disruptor 生产者
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer buffer){
        long sequence = ringBuffer.next();

        try {
            //通过sequence获取ringBuffer中的event对象
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(buffer.getLong(0));
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
