package com.example.market.core.controller;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;

import java.util.Objects;

/**
 * Базовая реализация {@link TableController}
 * @param <M>
 */
public abstract class BaseTableController<M extends Model<M>>
        implements TableController<M> {

    private Repository<M> repository;

    @Override
    public void delete(long index) {
        getRepository().delete(index);
    }

    @Override
    public void save(M model) {
        getRepository().save(model);
    }

    @Override
    public M find(long id) {
        return getRepository().find(id);
    }

    protected Repository<M> getRepository() {
        return Objects.requireNonNull(repository, "Storage is null");
    }

    @Override
    public void setRepository(Repository<M> repository) {
        this.repository = repository;
    }
}
