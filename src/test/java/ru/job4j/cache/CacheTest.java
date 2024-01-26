package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    @Test
    public void whenAddFind() {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        assertThat(cache.add(base)).isTrue();
        var find = cache.findById(base.id());
        assertThat(find).isNotEmpty();
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        assertThat(cache.update(new Base(1, "Base updated", 1))).isTrue();
        var find = cache.findById(base.id());
        assertThat(find).isNotEmpty();
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() {
        var base = new Base(1,   "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find).isEmpty();
    }

    @Test
    public void whenMultiUpdateThrowException() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenAddDuplicateThenFalse() {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        assertThat(cache.add(base)).isFalse();
    }

    @Test
    public void whenUpdateMissingKeyThenFalse() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        assertThat(cache.update(new Base(2, "Updated base", 1))).isFalse();
    }
}