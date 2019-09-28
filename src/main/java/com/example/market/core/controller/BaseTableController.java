package com.example.market.core.controller;

import com.example.market.core.data.Storage;
import com.example.market.core.model.Model;

import java.util.Objects;

public abstract class BaseTableController<M extends Model<M>>
        implements TableController<M> {

    private Storage<M> storage;

    @Override
    public void delete(long index) {
        getStorage().delete(index);
    }

    @Override
    public void save(M model) {
        getStorage().save(model);
    }

    @Override
    public M find(long id) {
        return getStorage().find(id);
    }

    protected Storage<M> getStorage() {
        return Objects.requireNonNull(storage, "Storage is null");
    }

    @Override
    public void setStorage(Storage<M> storage) {
        this.storage = storage;
    }
}
