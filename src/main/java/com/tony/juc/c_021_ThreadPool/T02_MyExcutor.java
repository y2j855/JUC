package com.tony.juc.c_021_ThreadPool;

import java.util.concurrent.Executor;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 20:22
 * Description: Executor执行器
 * 作用：将线程的定义与运行分离
 * Executor有点像Runnable与线程/线程池的中介者
 */
public class T02_MyExcutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static void main(String[] args) {
        new T02_MyExcutor().execute(()-> {
            System.out.println("hello executor");
        });
    }
}
