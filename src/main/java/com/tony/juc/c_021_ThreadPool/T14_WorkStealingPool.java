package com.tony.juc.c_021_ThreadPool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/28 19:33
 * Description: WorkStealingPool
 * 每个线程都有自己单独的队列，当某一个线程运行完自己队列的线程，
 * 它会去其他线程的队列中区偷线程任务执行。
 * 底层就是调用了ForkJoinPool。
 */
public class T14_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        //由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        service.execute(new MyTask(1000));
        service.execute(new MyTask(2000));
        service.execute(new MyTask(2000));
        service.execute(new MyTask(2000));
        service.execute(new MyTask(2000));

        System.in.read();
    }

    static class MyTask implements Runnable{
        int time;

        public MyTask(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
