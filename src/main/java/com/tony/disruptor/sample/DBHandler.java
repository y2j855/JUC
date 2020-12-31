package com.tony.disruptor.sample;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/31 12:14
 * Description: 消费者 对Event进行操作，保存到数据库
 */
public class DBHandler implements EventHandler<Order>, WorkHandler<Order> {

    @Override
    public void onEvent(Order order, long l, boolean b) throws Exception {
        this.onEvent(order);
    }

    @Override
    public void onEvent(Order order) throws Exception {
        long threadId = Thread.currentThread().getId();
        String id = order.getId();
        System.out.println(String.format("%s：Thread Id %s 订单信息保存 %s 到数据库中 ....",
                this.getClass().getSimpleName(), threadId, id));
    }
}
