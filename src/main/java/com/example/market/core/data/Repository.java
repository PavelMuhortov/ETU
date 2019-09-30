package com.example.market.core.data;

import com.example.market.core.model.Model;

/**
 * Предоставляет CRUD методы к данным
 *
 * @param <M>
 */
public interface Repository<M extends Model<M>>
        extends DataSupplier<M> {

    void delete(long index);

    /**
     * Сохраняет модель. Метод должен устанавливать модели уникальный идентификатор. {@link Model#setId(long)}
     *
     * @param model
     */
    void save(M model);

    default M find(long index) {
        return getAll().stream()
                .filter(model -> model.getId() == index)
                .findFirst()
                .orElse(null);
    }
}
