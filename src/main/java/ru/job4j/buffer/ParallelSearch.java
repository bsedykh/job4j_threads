package ru.job4j.buffer;

public class ParallelSearch {
    private static final Integer STOP = -1;

    public static void main(String[] args) {
        var queue = new SimpleBlockingQueue<Integer>();
        var consumer = new Thread(
                () -> {
                    try {
                        for (Integer value = queue.poll();
                             !STOP.equals(value);
                             value = queue.poll()) {
                            System.out.println(value);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        var producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(STOP);
                }

        );
        producer.start();
    }
}
