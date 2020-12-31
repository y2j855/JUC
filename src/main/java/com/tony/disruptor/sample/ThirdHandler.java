package com.tony.disruptor.sample;

import com.lmax.disruptor.EventHandler;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/31 15:00
 * Description:将数据发到其他系统
 */
public class ThirdHandler implements EventHandler<Order> {

    @Override
    public void onEvent(Order order, long l, boolean b) throws Exception {
        long threadId = Thread.currentThread().getId();
        String id = order.getId();
        System.out.println(String.format("%s：Thread Id %s 订单信息 %s 发送到 karaf 系统中 ....",
                this.getClass().getSimpleName(), threadId, id));
    }
}
