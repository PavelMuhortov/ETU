package com.example.market.core.module;

import com.example.market.core.controller.TableController;
import com.example.market.core.data.Repository;
import com.example.market.core.model.Model;
import com.example.market.core.view.TableView;

public class TableModule<M extends Model<M>>
        implements Module {

    private TableView<M> view;

    public TableModule(Repository<M> repository, TableController<M> controller, TableView<M> view) {
        controller.setStorage(repository);
        view.setController(controller);
        view.setDataSupplier(repository);
        this.view = view;
    }

    @Override
    public void run() {
        view.show();
    }
}
