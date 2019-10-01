package com.example.market.core.controller;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;

import java.util.function.Supplier;

/**
 * Предоставляет методы управления данными для отображения для отображения
 * @param <M>
 */
public interface TableController<M extends Model<M>> {

    void delete(long id);

    void save(M model);

    M find(long id);

    M newOne();

    void setRepository(Repository<M> repository);

    static <M extends Model<M>> TableController<M> newInstance(Supplier<M> newModelFactory) {
        return new BaseTableController<>() {
            @Override
            public M newOne() {
                return newModelFactory.get();
            }
        };
    }

}
