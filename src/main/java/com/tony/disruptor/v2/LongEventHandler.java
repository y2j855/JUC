package com.tony.disruptor.v2;

import com.lmax.disruptor.EventHandler;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 19:25
 * Description: disruptor EventHandler
 * 处理Event对象
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public static long count = 0;

    /**
     * @param event         Event对象
     * @param sequence      RingBuffer的序号
     * @param endOfBatch    是否为最后一个元素，也就是没有生产者在生产了
     * @throws Exception
     */
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        count++;
        System.out.println("[" + Thread.currentThread().getName() + "]"
        + event + " 序号：" + sequence);
    }
}
