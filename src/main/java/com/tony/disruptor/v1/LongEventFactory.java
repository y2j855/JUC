package com.tony.disruptor.v1;

import com.lmax.disruptor.EventFactory;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 19:01
 * Description: disruptor EventFactory
 * 用来创建Event对象，默认会将对象初始化到ringBuffer中
 */
public class LongEventFactory implements EventFactory {

    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
