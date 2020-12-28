package com.tony.juc.c_021_ThreadPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/28 15:31
 * Description: ForkJoinPool 将大任务切分成多个小任务，执行完后再汇总成大任务返回结果
 */
public class T13_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static{
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }

        System.out.println(Arrays.stream(nums).sum());  //stream api
    }

    /**
     * 使用ForkJoinPool有两种方式
     * 不带返回值，就是将大任务切成小任务，小任务不会再进行汇总。
     */
    static class AddTask extends RecursiveAction{
        int start,end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if(end-start <= MAX_NUM){
                long sum =0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println("from:" + start + " to:" + end + " = " +sum);
            }else{
                int middle = start + (end-start)/2;

                AddTask subTask1 = new AddTask(start,middle);
                AddTask subTask2 = new AddTask(middle,end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }

    /**
     * 带返回值，大任务切分成多个小任务，小任务执行完在汇总到大任务。
     */
    static class AddTaskReturn extends RecursiveTask<Long>{
        private int start,end;

        public AddTaskReturn(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if(end-start <= MAX_NUM){
                long sum =0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }else{
                int middle = start + (end-start)/2;

                AddTaskReturn subTask1 = new AddTaskReturn(start,middle);
                AddTaskReturn subTask2 = new AddTaskReturn(middle,end);
                subTask1.fork();
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
//        AddTask task = new AddTask(0,nums.length);
//        fjp.execute(task);
//        System.in.read();   //因为ForkJoinPool是后台守护线程，所以要阻塞才能看到结果。

        AddTaskReturn taskReturn = new AddTaskReturn(0,nums.length);
        fjp.execute(taskReturn);
        long result = taskReturn.join();
        System.out.println(result);

    }



}
