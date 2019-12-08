package com.example.market.core.view;

import com.example.market.core.model.Model;
import com.example.market.core.viewmodel.TableViewModel;

import java.io.File;
import java.util.Collection;

/**
 * Отображение данных в табличном виде
 * @param <M>
 */
public interface TableView<M extends Model<M>> {

    /**
     * Инициализация отображения. {@link TableViewModel} должен быть установлен
     */
    void init();

    void showData(Collection<M> data);

    void alert(String alert);

    boolean confirm(String message);

    void open(File file);

    void setViewModel(TableViewModel<M> viewModel);

}
