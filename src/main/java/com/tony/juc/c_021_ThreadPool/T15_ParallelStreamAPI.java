package com.tony.juc.c_021_ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/28 19:38
 * Description: parallel stream api
 * 它的底层也是ForkJoinPool
 */
public class T15_ParallelStreamAPI {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            numbers.add(1000000 + r.nextInt(1000000));
        }

        long start = System.currentTimeMillis();
        numbers.forEach(v -> isPrime(v));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        //使用parallel stream api
        start = System.currentTimeMillis();
        numbers.parallelStream().forEach(v -> isPrime(v));
        end = System.currentTimeMillis();
        System.out.println(end - start);

        //TODO lambda表达式总结 与stream配合使用的总结
    }

    private static boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
