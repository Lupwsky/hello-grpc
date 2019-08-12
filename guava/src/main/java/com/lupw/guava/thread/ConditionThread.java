package com.lupw.guava.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lupengwei 2019/8/12
 */
@Slf4j
public class ConditionThread {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        PrintThread printThreadA = new PrintThread(lock, conditionA, conditionB, "A");
        PrintThread printThreadB = new PrintThread(lock, conditionB, conditionC, "B");
        PrintThread printThreadC = new PrintThread(lock, conditionC, conditionA, "C");

        printThreadA.start();
        Thread.sleep(100);
        printThreadB.start();
        Thread.sleep(100);
        printThreadC.start();
    }

    public static class PrintThread extends Thread {

        private ReentrantLock lock;
        private Condition condition1;
        private Condition condition2;
        private String printContent;

        public PrintThread(ReentrantLock lock, Condition condition1, Condition condition2, String printContent) {
            this.lock = lock;
            this.condition1 = condition1;
            this.condition2 = condition2;
            this.printContent = printContent;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    log.info(printContent);
                    condition2.signal();

                    // 输出 10 次信息后不在等待, 让线程顺利执行
                    if (i < 9) {
                        condition1.await();
                    }
                }
            } catch (Exception e) {
                log.error("error message", e);
            } finally {
                lock.unlock();
            }
        }
    }
}
