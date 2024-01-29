package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {
    @Test
    public void whenSyncSum() {
        var matrix = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        var expected = new Sums[] {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        var result = RolColSum.sum(matrix);
        assertThat(Arrays.equals(result, expected)).isTrue();
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        var matrix = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        var expected = new Sums[] {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        var result = RolColSum.asyncSum(matrix);
        assertThat(Arrays.equals(result, expected)).isTrue();
    }
}