package com.tony.juc.c_020_interview_A1B2C3;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 19:49
 * Description: 知识拓展
 * PipedStream:就是线程之间的管道，性能比较差。
 * 需要用IO进行操作。
 */
public class T09_PipedStream {
    public static void main(String[] args) throws IOException {
        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();

        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String msg = "Your Turn";

        new Thread(() -> {
            byte[] buffer = new byte[9];

            try {
                for (int i = 1; i < 27; i++) {
                    input1.read(buffer);
                    if (new String(buffer).equals(msg)) {
                        System.out.println(i);
                    }
                    output1.write(msg.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            byte[] buffer = new byte[9];

            try {
                for (int i = 65; i < 91; i++) {
                    char c = (char) i;
                    System.out.println(c);

                    output2.write(msg.getBytes());
                    input2.read(buffer);
                    if (new String(buffer).equals(msg)) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
