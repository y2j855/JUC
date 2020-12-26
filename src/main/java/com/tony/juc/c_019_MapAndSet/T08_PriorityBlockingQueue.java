package com.tony.juc.c_019_MapAndSet;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 16:22
 * Description: 同步小根堆，如果自定义排序逻辑需要实现Compable接口。
 * 保证线程安全的。
 */
public class T08_PriorityBlockingQueue {
    public static void main(String[] args) {
        PriorityBlockingQueue<User> queue = new PriorityBlockingQueue();

        for (int i = 0; i < 12; i++) {
            User user = new User();
            int max = 20;
            int min = 10;
            Random r = new Random();

            int n = r.nextInt(max) % (max - min + 1) + min;

            user.setPriority(n);
            user.setUserName("张三" + i);

            queue.add(user);
        }

        for (int i = 0; i < 12; i++) {
            User u = queue.poll();
            System.out.println("优先级是:" + u.getPriority() + "," + u.getUserName());
        }

    }

}

class User implements Comparable<User> {
    private Integer priority;
    private String userName;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int compareTo(User user) {
        return this.priority.compareTo(user.getPriority());
    }
}
