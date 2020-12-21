package com.tony.juc.c_016_RefTypeAndThreadLocal;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 20:27
 * Description: 强弱软虚测试用的对象
 */
public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
