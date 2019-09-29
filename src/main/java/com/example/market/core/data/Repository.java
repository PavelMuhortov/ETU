package com.example.market.core.data;

import com.example.market.core.model.Model;

/**
 * Предоставляет CRUD методы к данным
 * @param <M>
 */
public interface Repository<M extends Model<M>>
        extends DataSupplier<M> {

    void delete(long index);

    void save(M model);

    M find(long index);
}
