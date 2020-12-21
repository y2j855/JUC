package com.tony.juc.c_015_VarHandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 19:51
 * Description: VarHandle用法
 * VarHandle是能够任何类型对象的引用，并且能够保证普通对象的原子操作。
 * 它的性能比反射要高很多，本质就是直接操作二进制码
 */
public class T01_HelloVarHandle {
    int x = 8;

    private static VarHandle handle;

    static{
        try {
            handle = MethodHandles.lookup().findVarHandle(T01_HelloVarHandle.class,"x",int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        T01_HelloVarHandle t = new T01_HelloVarHandle();

        /**
         * read / write
         * 能够获得对象的引用，并且它的操作是原子性
         * 不用加锁就能保证原子性。
         */
        System.out.println(handle.get(t));
        handle.set(t,9);
        System.out.println(t.x);

        handle.compareAndSet(t,9,10);
        System.out.println(t.x);

        handle.getAndAdd(t,10);
        System.out.println(t.x);
    }
}
