package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()
            && count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var countBarrier = new CountBarrier(5);
        var thread = new Thread(() -> {
            var threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": started");
            countBarrier.await();
            System.out.println(threadName + ": finished");
        });
        thread.start();
        for (int i = 0; i < 5; i++) {
            countBarrier.count();
            Thread.sleep(1000);
        }
    }
}
