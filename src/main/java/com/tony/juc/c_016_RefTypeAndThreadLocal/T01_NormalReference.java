package com.tony.juc.c_016_RefTypeAndThreadLocal;

import java.io.IOException;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 20:29
 * Description: 强引用=普通对象的声明方式
 */
public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc();

        System.in.read();
    }
}
