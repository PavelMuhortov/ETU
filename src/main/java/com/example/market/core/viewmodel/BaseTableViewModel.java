package com.example.market.core.viewmodel;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;
import com.example.market.core.view.TableView;

import java.util.Objects;

/**
 * Базовая реализация {@link TableViewModel}
 *
 * @param <M>
 */
public abstract class BaseTableViewModel<M extends Model<M>>
        implements TableViewModel<M> {

    private Repository<M> repository;
    private TableView<M> view;

    private String property;
    private String value;

    @Override
    public void delete(long index) {
        getRepository().delete(index);
        showData();
    }

    @Override
    public void save(M model) {
        getRepository().save(model);
    }

    protected Repository<M> getRepository() {
        return Objects.requireNonNull(repository, "Storage is null");
    }

    @Override
    public void setRepository(Repository<M> repository) {
        this.repository = repository;
    }

    @Override
    public void setView(TableView<M> view) {
        this.view = view;
    }

    @Override
    public void search(String property, String value) {
        this.property = property;
        this.value = value;
        showData();
    }

    @Override
    public void reset() {
        this.property = null;
        this.value = null;
        showData();
    }

    @Override
    public void refresh() {
        showData();
    }

    private void showData() {
        try {
            if (null != this.property && null != this.value) {
                view.showData(getRepository().findBy(property, value));
            } else {
                view.showData(getRepository().getAll());
            }
        } catch (Exception e) {
            view.alert("Невозможно загрузить данные." + e.getLocalizedMessage());
        }
    }
}
