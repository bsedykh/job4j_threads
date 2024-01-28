package ru.job4j.pool;

import org.junit.jupiter.api.Test;

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
        var result = RolColSum.sum(matrix);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getRowSum()).isEqualTo(15);
        assertThat(result[2].getRowSum()).isEqualTo(24);
        assertThat(result[0].getColSum()).isEqualTo(12);
        assertThat(result[1].getColSum()).isEqualTo(15);
        assertThat(result[2].getColSum()).isEqualTo(18);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        var matrix = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        var result = RolColSum.asyncSum(matrix);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getRowSum()).isEqualTo(15);
        assertThat(result[2].getRowSum()).isEqualTo(24);
        assertThat(result[0].getColSum()).isEqualTo(12);
        assertThat(result[1].getColSum()).isEqualTo(15);
        assertThat(result[2].getColSum()).isEqualTo(18);
    }
}