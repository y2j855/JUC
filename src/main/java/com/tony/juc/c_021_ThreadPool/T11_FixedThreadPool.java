package com.tony.juc.c_021_ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/28 11:26
 * Description: FixedThreadPool 固定线程个数的线程池
 * 它可以并行执行任务
 */
public class T11_FixedThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        serial();
        fixedThreadPool();
        completableFuture();
    }

    /**
     * 利用completableFuture运行并行任务
     */
    private static void completableFuture() {
        long start = System.currentTimeMillis();
        CompletableFuture<List<Integer>> future1 = CompletableFuture.supplyAsync(()->getPrime(1, 80000));
        CompletableFuture<List<Integer>> future2 = CompletableFuture.supplyAsync(()->getPrime(80001, 130000));
        CompletableFuture<List<Integer>> future3 = CompletableFuture.supplyAsync(()->getPrime(130001, 170000));
        CompletableFuture<List<Integer>> future4 = CompletableFuture.supplyAsync(()->getPrime(170001, 200000));

        CompletableFuture.allOf(future1,future2,future3,future4).join();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 利用fixedThreadPool运行并行任务
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void fixedThreadPool() throws InterruptedException, ExecutionException {
        final int cpuCoreNum = 4;

        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

        MyTask t1 = new MyTask(1, 80000);
        MyTask t2 = new MyTask(80001, 130000);
        MyTask t3 = new MyTask(130001, 170000);
        MyTask t4 = new MyTask(170001, 200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        long start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 串行执行
     */
    private static void serial() {
        long start = System.currentTimeMillis();
        getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;

        public MyTask(int startPos, int endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }


        @Override
        public List<Integer> call() throws Exception {
            List<Integer> list = getPrime(startPos, endPos);
            return list;
        }
    }

    private static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                results.add(i);
            }
        }
        return results;
    }

    private static boolean isPrime(int number) {
        for (int i = 2; i < number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
