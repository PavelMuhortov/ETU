package com.example.market.core.viewmodel;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Предоставляет методы управления данными для отображения
 *
 * @param <M>
 */
public interface TableViewModel<M extends Model<M>> {

    static <M extends Model<M>> TableViewModel<M> newInstance(Supplier<M> newModelFactory) {
        return new BaseTableViewModel<>() {
            @Override
            public M newOne() {
                return newModelFactory.get();
            }
        };
    }

    void delete(long id);

    void save(M model);

    M find(long id);

    M newOne();

    void setRepository(Repository<M> repository);

    Collection<M> findBy(String property, String value);

    Collection<M> getAll();
}
