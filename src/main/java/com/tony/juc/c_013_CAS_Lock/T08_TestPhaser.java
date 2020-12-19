package com.tony.juc.c_013_CAS_Lock;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/19 08:28
 * Description: Phaser 按照不同的阶段对线程进行执行
 *
 * 基本认识
 */
public class T08_TestPhaser {
    static Random random = new Random();
    static Phaser phaser = new MarriagePhaser();

    private static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase){
                case 0:
                    System.out.println("所有人都到齐了！");
                    return false;
                case 1:
                    System.out.println("所有人都吃完了！");
                    return false;
                case 2:
                    System.out.println("所有人都离开了！");
                    return false;
                case 3:
                    System.out.println("所有人都回家了！");
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person{
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive(){
            sleepTime(random.nextInt(1000));
            System.out.printf("%s 到达现场！\n", name);
        }


        public void eat(){
            sleepTime(random.nextInt(1000));
            System.out.printf("%s 吃完!\n", name);
        }

        public void leave(){
            sleepTime(random.nextInt(1000));
            System.out.printf("%s 离开！\n", name);
        }

        public void goHome(){
            sleepTime(random.nextInt(1000));
            System.out.printf("%s 回家！\n",name);
        }
    }

    private static void sleepTime(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        phaser.bulkRegister(5);
        for (int i = 0; i < 5; i++) {
            final int nameIndex = i;
            new Thread(()->{
                Person p = new Person("person " + nameIndex);
                p.arrive();
                phaser.arriveAndAwaitAdvance();

                p.eat();
                phaser.arriveAndAwaitAdvance();

                p.leave();
                phaser.arriveAndAwaitAdvance();

                p.goHome();
                phaser.arriveAndAwaitAdvance();
            }).start();

        }
    }
}
