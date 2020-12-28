package com.tony.juc.c_021_ThreadPool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 20:31
 * Description: CompletableFuture:更高级的Future。
 * 它是用来管理Future
 * 应用场景
 * 假设你能够提供一个服务
 * 这个服务查询各大电商网站同一类产品的价格并汇总展示
 *
 * 实现比较复杂，底层用的是ForkJoinPool。
 */
public class T04_CompletableFuture {
    public static void main(String[] args) {
        long start, end;

        //串行运行
        start = System.currentTimeMillis();
        priceOfTM();
        priceOfTB();
        priceOfJD();
        end = System.currentTimeMillis();
        System.out.println("use serial method call!" +(end-start));

        start = System.currentTimeMillis();

        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceOfJD());

        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();

        //通过lambda表达式方式调用，更加灵活。
        CompletableFuture.supplyAsync(() -> priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(str -> "price" + str)
                .thenAccept(System.out::println);//异步的

        end = System.currentTimeMillis();
        System.out.println("use completable future!" + (end - start));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static double priceOfJD() {
        delay();
        return 3.00;
    }

    private static double priceOfTB() {
        delay();
        return 2.00;
    }

    private static double priceOfTM() {
        delay();
        return 1.00;
    }

    private static void delay() {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("After %s sleep!\n", time);
    }
}
