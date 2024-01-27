package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T value;
    private final T[] array;
    private final int from;
    private final int to;

    private ParallelIndexSearch(T value, T[] array, int from, int to) {
        this.value = value;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch(from, to);
        }
        var middle = (from + to) / 2;
        var leftSearch = new ParallelIndexSearch<>(value, array, from, middle);
        var rightSearch = new ParallelIndexSearch<>(value, array, middle, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        var result = Math.min(left, right);
        return result != -1 ? result : Math.max(left, right);
    }

    private Integer linearSearch(int from, int to) {
        int result = -1;
        for (int i = from; i < to; i++) {
            if (array[i].equals(value)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static <T> int indexOf(T value, T[] array) {
        return new ForkJoinPool().invoke(
                new ParallelIndexSearch<>(value, array, 0, array.length)
        );
    }
}
