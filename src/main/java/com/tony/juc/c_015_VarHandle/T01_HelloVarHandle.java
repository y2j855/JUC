package com.tony.juc.c_015_VarHandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 19:51
 * Description: VarHandle用法
 * VarHandle是能够任何类型对象的引用，并且能够保证普通对象的原子操作。
 * 它的性能比反射要高很多，本质就是直接操作二进制码
 *
 * VarHandle五种访问模式
 * 1.read access mode,such as reading a variable with volatile memory ordering effects
 * 2.write access modes,such as updating a variable with release memory ordering effects
 * 3.atomic update access modes,such as a compare-and-set on a variable with volatile
 * memory order effects for both read and writing.
 * 4.numeric atomic update access modes,such as get-and-add with plain memory order
 * effects for writing and acquire memory order effects for reading.
 * 5.bitwise atomic update access modes,such as get-and-bitwise-and with release
 * memory order effects for writing and plain memory order effects for reading.
 *
 * 1.读数据，保证原子性
 * 2.写数据，保证原子性
 * 3.支持CAS操作
 * 4.支持GAA操作
 * 5.支持防止指令重排
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

        //CAS操作
        handle.compareAndSet(t,9,10);
        System.out.println(t.x);

        //GAA操作
        handle.getAndAdd(t,10);
        System.out.println(t.x);

        //防止指令重排序

    }
}
