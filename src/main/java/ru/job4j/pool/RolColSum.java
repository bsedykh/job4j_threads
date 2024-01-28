package ru.job4j.pool;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        var result = new Sums[matrix.length];
        for (var i = 0; i < matrix.length; i++) {
            result[i] = new Sums();
        }
        for (var i = 0; i < matrix.length; i++) {
            for (var j = 0; j < matrix.length; j++) {
                result[i].rowSum += matrix[i][j];
                result[j].colSum += matrix[i][j];
            }
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        var result = new Sums[matrix.length];
        var futures = new ArrayList<CompletableFuture<Sums>>(matrix.length);
        for (var rowCol = 0; rowCol < matrix.length; rowCol++) {
            var rc = rowCol;
            futures.add(CompletableFuture.supplyAsync(() -> {
                var sums = new Sums();
                for (int j = 0; j < matrix.length; j++) {
                    sums.rowSum += matrix[rc][j];
                }
                for (int i = 0; i < matrix.length; i++) {
                    sums.colSum += matrix[i][rc];
                }
                return sums;
            }));
        }
        for (int i = 0; i < matrix.length; i++) {
            result[i] = futures.get(i).get();
        }
        return result;
    }
}
