package ru.job4j.cache;

public class UncheckedOptimisticException extends RuntimeException {
    public UncheckedOptimisticException(String message) {
        super(message);
    }
}
