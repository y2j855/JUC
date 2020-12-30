package com.tony.disruptor.v2;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 20:08
 * Description: 生产者 使用Translator来处理信息
 */
public class LongEventProducer {
    private static final EventTranslatorOneArg<LongEvent,ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        @Override
        public void translateTo(LongEvent event, long l, ByteBuffer byteBuffer) {
            event.setValue(byteBuffer.getLong(0));
        }
    };
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将发布信息封装到Translator中，这样讲生产者与发布信息到ringBuffer的操作解耦
     * 这样我们只需要在Translator中处理业务逻辑就可以，单元测试也比较好做。
     * @param buffer
     */
    public void onData(ByteBuffer buffer){
        ringBuffer.publishEvent(TRANSLATOR,buffer);
    }
}
