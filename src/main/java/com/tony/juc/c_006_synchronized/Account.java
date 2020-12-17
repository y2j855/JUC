package com.tony.juc.c_006_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 11:11
 * Description: synchronized
 * 面试题：模拟银行账户
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 这样行不行？（根据业务要求）
 *
 * 容易产生脏读问题(dirtyRead)
 */
public class Account {
    String name;
    double balance;

    public synchronized void set(String name,double balance){
        this.name =name;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(()->a.set("zhangsan",100.0),"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));

    }
}
