package com.example.market.core.view;

import com.example.market.core.controller.TableController;
import com.example.market.core.data.DataSupplier;
import com.example.market.core.model.Model;

public interface TableView<M extends Model<M>> {

    void show();

    void setController(TableController<M> controller);

    void setDataSupplier(DataSupplier<M> dataSupplier);
}
