package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        boolean result;
        try {
            result = memory.computeIfPresent(model.id(), (key, value) -> {
                if (value.version() != model.version()) {
                    throw new UncheckedOptimisticException("Versions are not equal");
                }
                return new Base(model.id(), model.name(), model.version() + 1);
            }) != null;
        } catch (UncheckedOptimisticException e) {
            throw new OptimisticException(e.getMessage());
        }
        return result;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
