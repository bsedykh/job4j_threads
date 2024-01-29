package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    public void whenIncrementConcurrently() throws InterruptedException {
        var counter = new CASCount();
        var thread1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        });
        var thread2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(counter.get()).isEqualTo(20_000);
    }
}
