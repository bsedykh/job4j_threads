package ru.job4j.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> send(
                "Notification %s to email %s".formatted(user.username(), user.email()),
                "Add a new event to %s".formatted(user.username()),
                user.email())
        );
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        pool.shutdown();
    }
}
