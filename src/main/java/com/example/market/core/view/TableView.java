package com.example.market.core.view;

import com.example.market.core.controller.TableController;
import com.example.market.core.data.DataSupplier;
import com.example.market.core.model.Model;

/**
 * Отображение данных в табличном виде
 * @param <M>
 */
public interface TableView<M extends Model<M>> {

    /**
     * Инициализация отображения. {@link DataSupplier} и {@link TableController} должны быть установлены
     */
    void init();

    void setController(TableController<M> controller);

    void setDataSupplier(DataSupplier<M> dataSupplier);
}
