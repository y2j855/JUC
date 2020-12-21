package com.tony.juc.c_016_RefTypeAndThreadLocal;

import java.lang.ref.WeakReference;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 20:41
 * Description: 弱引用
 * 弱引用只要GC就会回收
 * 经常和强引用配合使用，常用场景容器。
 * ThreadLocal，ConcurrentHashMap
 */
public class T03_WeakReference {
    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        //使用完一定要加remove，否则会导致内存泄露
        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();
    }
}
