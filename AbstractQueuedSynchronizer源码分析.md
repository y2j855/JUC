# AbstractQueuedSynchronizer源码分析

## 1.背景

AQS（java.util.concurrent.locks.AbstractQueuedSynchronizer）是Doug Lea大师创作的，用来构建锁或者其他同步组件（信号量、事件等）的基础框架类。JDK中许多并发工具类的内部实现都依赖于AQS，如ReentrantLock,Semaphore,CountDownLatch等等。学习AQS的使用与源码实现对深入理解concurrent包中的类有很大的帮助。

本文重点介绍AQS中的基本实现思路，包括独占锁、共享锁的获取和释放实现原理和一些代码细节。

## 2.简介

AQS的主要使用方式是继承它作为一个内部辅助类实现同步原语，它可以简化你的并发工具的内部实现，屏蔽同步状态管理、线程的排队、等待与唤醒等底层操作。

AQS设计基于模板方法设计模式(callback)，开发者需要继承同步器并且重写指定的方法，将其组合在并发组件的实现中，调用同步器的模板方法，模板方法会调用使用者重写的方法。

## 3.实现思路

介绍一下AQS具体实现的大致思路。

AQS内部维护了一个CLH队列来管理锁。

线程会首先尝试获取锁，如果失败，则将当前线程以及等待状态等信息包装成一个Node节点加入到同步队列里。

接着会不断循环尝试获取锁（条件是当前节点为head的直接后继才会尝试），如果失败则会阻塞自己，直至被唤醒。

而当持有锁的线程释放锁时，会唤醒队列中的后继线程。

下面列举JDK中几种常见使用了AQS的同步组件：

* ReentrantLock：使用了AQS的独占锁获取和释放，用state变量记录某个线程获取独占锁的次数，获取锁时+1，释放锁时-1，在获取时会校验线程是否可以获取锁。
* Semaphore：使用了AQS的共享锁获取和释放，用state变量作为计数器，只有在大于0时允许线程进入。获取锁时-1，释放锁时+1。
* CountDownLatch：使用了AQS的共享锁获取和释放，用state变量作为计数器，在初始化时指定。只要state还大于0，获取共享锁会因为失败而阻塞，直到计数器的值为0时，共享锁才允许获取，所有等待线程会被逐一唤醒。

1. 如何获取锁

   获取锁的思路：

   ```java
   while(不满足获取锁的条件){
     把当前线程包装成节点插入同步队列
       if(需要阻塞当前线程)
         阻塞当前线程直至被唤醒
   }
   将当前线程从同步队列中移除
   ```

   以上是一个很简单的获取锁的伪代码流程，AQS的具体实现要复杂一些，也稍有不同，但思想上是与上述伪代码契合的。

   通过循环检查是否能够获取到锁，如果不满足，则可能会被阻塞，直至被唤醒。

2. 如何释放锁

   释放锁的过程设计修改同步状态，以及唤醒后继等待线程

   ```java
   修改同步状态
   if(修改后的状态允许其他线程获取到锁)
     唤醒后继线程
   ```

   这只是很简单的释放锁的伪代码，AQS具体实现中能看到这个简单的流程模型。

3. API简介

   通过上面的AQS大体思路分析，我们可以看到，AQS主要做了三件事情

   - 同步状态的管理

   - 线程的阻塞和唤醒

   - 同步队列的维护

   下面三个protected final方法是AQS中用来访问/修改同步状态的方法：

   - int getState():获取同步状态
   - void setState():设置同步状态
   - boolean compareAndSetState(int expect,int update):基于CAS，原子设置当前状态

   在自定义基于AQS的同步工具时，我们可以选择覆盖实现以下几个方法来实现同步状态的管理：

   | 方法                             | 描述                     |
   | :------------------------------- | ------------------------ |
   | boolean tryAcquire(int arg)      | 尝试获取独占锁           |
   | boolean tryRelease(int arg)      | 尝试释放独占锁           |
   | int tryAcquireShared(int arg)    | 尝试获取共享锁           |
   | boolean tryReleaseShared(nt arg) | 尝试释放共享锁           |
   | boolean isHeldExclusively()      | 当前线程是否获得了独占锁 |

   以上的几个尝试获取/释放锁的方法的具体实现应该是无阻塞的。

   AQS本身将同步状态的管理用模板方法模式都封装好了，以下列举了AQS中的一些模板方法：

   | 方法                                              | 描述                                                         |
   | :------------------------------------------------ | ------------------------------------------------------------ |
   | void acquire(int arg)                             | 获取独占锁。会调用tryAcquire方法，如果未获取成功，则会进入同步队列等待 |
   | void acquireInterruptibly(int arg)                | 响应中断版本的acquire                                        |
   | boolean tryAcquireNanos(int arg, long nanos)      | 响应中断+带超时版本的acquire                                 |
   | void acquireShared(int arg)                       | 获取共享锁。会调用tryAcquireShared方法                       |
   | void acquireSharedInterruptibly(int arg)          | 响应中断版本的acquireShared                                  |
   | boolean tryAcquireSharedNanos(int arg,long nanos) | 响应中断+带超时版本的acquireShared                           |
   | boolean release(int arg)                          | 释放独占锁                                                   |
   | boolean releaseShared(int arg)                    | 释放共享锁                                                   |
   | Collection getQueueThreads()                      | 获取同步队列上的线程集合                                     |

   上面看上去很多方法，其实从语义上来区分就是获取和释放，从模式上区分就是独占式和共享式，从中断响应上来看就是支持和不支持。

   

## 4.AQS图解

1. 数据结构图

   ![数据结构]()

2. Node添加节点，删除节点过程
   

## 5.代码解读

1. 数据结构定义

   * Node对象

   ```java
   static final class Node {
       /**
        * 用于标记一个节点在共享模式下等待
        */
       static final Node SHARED = new Node();
   
       /**
        * 用于标记一个节点在独占模式下等待
        */
       static final Node EXCLUSIVE = null;
   
       /**
        * 等待状态：取消
        */
       static final int CANCELLED = 1;
   
       /**
        * 等待状态：通知
        */
       static final int SIGNAL = -1;
   
       /**
        * 等待状态：条件等待
        */
       static final int CONDITION = -2;
   
       /**
        * 等待状态：传播
        */
       static final int PROPAGATE = -3;
   
       /**
        * 等待状态
        */
       volatile int waitStatus;
   
       /**
        * 前驱节点
        */
       volatile Node prev;
   
       /**
        * 后继节点
        */
       volatile Node next;
   
       /**
        * 节点对应的线程
        */
       volatile Thread thread;
   
       /**
        * 等待队列中的后继节点
        */
       Node nextWaiter;
   
       /**
        * 当前节点是否处于共享模式等待
        */
       final boolean isShared() {
           return nextWaiter == SHARED;
       }
   
       /**
        * 获取前驱节点，如果为空的话抛出空指针异常
        */
       final Node predecessor() throws NullPointerException {
           Node p = prev;
           if (p == null) {
               throw new NullPointerException();
           } else {
               return p;
           }
       }
   
       Node() {
       }
   
       /**
        * addWaiter会调用此构造函数
        */
       Node(Thread thread, Node mode) {
           this.nextWaiter = mode;
           this.thread = thread;
       }
   
       /**
        * Condition会用到此构造函数
        */
       Node(Thread thread, int waitStatus) {
           this.waitStatus = waitStatus;
           this.thread = thread;
       }
   }
   ```

   * 由于AQS源码中有大量的状态判断与跃迁，所以需要梳理等待状态的定义

     AQS状态的流转图：

     | 值            | 描述                                                         |
     | ------------- | ------------------------------------------------------------ |
     | CANCELLED(1)  | 当前线程因为超时或者中断被取消。这是一个终结态，也就是状态到此为止。 |
     | SIGNAL(-1)    | 当前线程的后继线程被阻塞或者即将被阻塞，当前线程释放锁或者取消后需要唤醒后继线程。这个状态一般都是后继线程来设置前驱节点。 |
     | CONDITION(-2) | 当前线程在condition队列中。                                  |
     | PROPAGATE(-3) | 用于将唤醒后继线程传递下去，这个状态的引入是为了完善和增强共享锁的唤醒机制。在一个节点称为头节点之前，是不会跃迁为此状态的。 |
     | 0             | 表示无状态。                                                 |

     

2. 获得独占锁的实现

   * 获取独占锁时序图
   * 获取独占锁逻辑流程图

3. 释放独占锁的实现

4. 获取共享锁的实现

5. 释放共享锁的实现

## 6.一些思考

1. 唤醒节点时为什么从tail向前遍历
2. unparkSuccessor有新线程争锁是否存在漏洞
3. AQS如何保证队列活跃
4. PROPAGATE状态存在的意义
5. AQS如何防止内存泄露

## 7.总结

## 8.参考

[AQS论文](http://gee.cs.oswego.edu/dl/papers/aqs.pdf)

The Art of Multiprocessor Programming(多处理器编程的艺术) 





