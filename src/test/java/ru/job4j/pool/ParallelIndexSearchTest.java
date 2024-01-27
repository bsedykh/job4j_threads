package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class ParallelIndexSearchTest {
    @Test
    public void whenLinearSearch() {
        var array = new Integer[] {10, -2, 3};
        assertThat(ParallelIndexSearch.indexOf(-2, array)).isEqualTo(1);
    }

    @Test
    public void whenRecursiveSearchAndEvenArrayLength() {
        var array = new Integer[] {10, -2, 3, 100, 5, 2, 7, -500, 90, 88, 11, 22};
        assertThat(ParallelIndexSearch.indexOf(90, array)).isEqualTo(8);
    }

    @Test
    public void whenRecursiveSearchAndOddArrayLength() {
        var array = new Integer[] {10, -2, 3, 100, 5, 2, 7, -500, 90, 88, 11};
        assertThat(ParallelIndexSearch.indexOf(11, array)).isEqualTo(10);
    }

    @Test
    public void whenElementNotFound() {
        var array = new Integer[] {10, -2, 3, 100, 5, 2, 7, -500, 90, 88, 11};
        assertThat(ParallelIndexSearch.indexOf(999, array)).isEqualTo(-1);
    }

    @Test
    public void whenMultipleMatchesThenMinIndex() {
        var array = new Integer[] {10, -2, 3, 100, 5, 2, 7, -2, 90, 88, -2};
        assertThat(ParallelIndexSearch.indexOf(-2, array)).isEqualTo(1);
    }

    @Test
    public void whenArrayOfStrings() {
        var array = new String[] {"find me", "-2", "3", "100", "5", "2", "7", "-2", "90", "8saa8", "test"};
        assertThat(ParallelIndexSearch.indexOf("find me", array)).isEqualTo(0);
    }
}