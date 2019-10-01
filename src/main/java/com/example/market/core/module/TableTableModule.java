package com.example.market.core.module;

import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;
import com.example.market.core.view.TableView;
import com.example.market.core.viewmodel.TableViewModel;

/**
 * Компонует и настраивает Model-View-ViewModel связку приложения.
 *
 * @param <M>
 */
public class TableTableModule<M extends Model<M>, V extends TableView<M>, VM extends TableViewModel<M>, R extends Repository<M>> {

    private final R repository;
    private final VM viewModel;
    private final V view;

    public TableTableModule(R repository, VM viewModel, V view) {
        this.repository = repository;
        viewModel.setRepository(this.repository);
        this.viewModel = viewModel;
        this.view = view;
        this.view.setViewModel(this.viewModel);
        view.init();
    }

    public M getModel() {
        return viewModel.newOne();
    }

    public V getView() {
        return view;
    }

    public VM getViewModel() {
        return viewModel;
    }

    public R getRepository() {
        return repository;
    }
}
