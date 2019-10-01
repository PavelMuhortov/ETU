package com.example.market.core.viewmodel;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;

import java.util.Collection;
import java.util.Objects;

/**
 * Базовая реализация {@link TableViewModel}
 *
 * @param <M>
 */
public abstract class BaseTableViewModel<M extends Model<M>>
        implements TableViewModel<M> {

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

    @Override
    public Collection<M> findBy(String property, String value) {
        return repository.findBy(property, value);
    }

    @Override
    public Collection<M> getAll() {
        return repository.getAll();
    }
}
