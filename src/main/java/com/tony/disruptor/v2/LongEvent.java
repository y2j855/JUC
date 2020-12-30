package com.tony.disruptor.v2;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/30 18:59
 * Description: disruptor Event对象
 */
public class LongEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
