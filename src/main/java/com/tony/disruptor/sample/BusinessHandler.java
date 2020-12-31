package com.tony.disruptor.sample;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/31 15:02
 * Description:消费者 处理业务逻辑
 */
public class BusinessHandler implements EventHandler<Order>, WorkHandler<Order> {

    @Override
    public void onEvent(Order order, long l, boolean b) throws Exception {
        onEvent(order);
    }

    @Override
    public void onEvent(Order order) throws Exception {
        long threadId = Thread.currentThread().getId();
        String id = order.getId();
        System.out.println(String.format("%s：Thread Id %s 订单信息 %s 处理中 ....",
                this.getClass().getSimpleName(), threadId, id));
    }
}
