package com.example.market.consoleview.model;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStorage<M extends Model<M>>
        implements Repository<M> {

    protected final Map<Long, M> data = new ConcurrentHashMap<>();

    private AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public Collection<M> getAll() {
        return Collections.unmodifiableCollection(data.values());
    }

    @Override
    public void save(M model) {
        if (model.getId() == 0) {
            final long id = generateId();
            model.setId(id);
        }
        data.put(model.getId(), model);
    }

    @Override
    public M find(long index) {
        return data.get(index);
    }

    @Override
    public void delete(long index) {
        data.remove(index);
    }

    private long generateId() {
        return idGenerator.incrementAndGet();
    }

}
