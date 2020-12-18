package com.tony.juc.c_012_02_Unsafe;

import sun.misc.Unsafe;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 09:21
 * Description: Unsafe类
 * Unsafe相当于c，c++操作内存的能力
 * 它能够对内存进行直接操作
 * AtomicXXX都是使用Unsafe来实现CAS
 * weakCompareAndSet/Swap()...
 *
 * jdk11之后将Unsafe关闭了，通过反射也不能使用了。
 */
public class HelloUnsafe {
    static class M{
        private M(){}
        int i = 0;
    }

    public static void main(String[] args) throws InstantiationException {
        Unsafe unsafe = Unsafe.getUnsafe();
        M m = (M) unsafe.allocateInstance(M.class);
        m.i = 9;
        System.out.println(m.i);

    }
}
