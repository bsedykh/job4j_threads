package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    @Test
    public void whenOfferOneValue() throws InterruptedException {
        var blockingQueue = new SimpleBlockingQueue<Integer>();
        var producer = new Thread(() -> blockingQueue.offer(100));
        var consumer = new Thread(() -> {
            try {
                assertThat(blockingQueue.poll()).isEqualTo(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
