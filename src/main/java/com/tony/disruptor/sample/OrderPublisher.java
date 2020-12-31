package com.tony.disruptor.sample;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/31 15:05
 * Description: 生产者
 */
public class OrderPublisher{

    Disruptor<Order> disruptor;
    private CountDownLatch latch;

    private static int LOOP = 1;

    public OrderPublisher(Disruptor<Order> disruptor, CountDownLatch latch) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    public void sendData(){
        OrderEventTranslator orderTranslator = new OrderEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            disruptor.publishEvent(orderTranslator);
        }
        latch.countDown();
    }
}

class OrderEventTranslator implements EventTranslator<Order>{

    @Override
    public void translateTo(Order order, long l) {
        order.setId(UUID.randomUUID().toString());
    }
}
