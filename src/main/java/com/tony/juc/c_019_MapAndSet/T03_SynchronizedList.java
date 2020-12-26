package com.tony.juc.c_019_MapAndSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 15:19
 * Description: 使用Collections.sync使list线程安全
 * 类似syncHashMap
 */
public class T03_SynchronizedList {
    List<String> list = new ArrayList<>();
    List<String> syncList = Collections.synchronizedList(list);
}
