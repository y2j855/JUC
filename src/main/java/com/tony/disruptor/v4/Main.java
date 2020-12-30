package com.tony.disruptor.v4;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 20:32
 * Description:
 */
public class Main {
    public static void handleEvent(LongEvent event,long sequence,boolean endOfBatch){
        System.out.println(event);
    }

    public static void translate(LongEvent event, long sequence, ByteBuffer buffer){
        event.setValue(buffer.getLong(0));
    }

    public static void main(String[] args){
        int bufferSize = 1<<10;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith(Main::handleEvent);

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long l = 0; true;l++) {
            bb.putLong(0,l);
            ringBuffer.publishEvent(Main::translate,bb);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
