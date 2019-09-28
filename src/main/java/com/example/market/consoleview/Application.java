package com.example.market.consoleview;

import com.example.market.consoleview.model.InMemoryStorage;
import com.example.market.consoleview.model.Product;
import com.example.market.consoleview.view.ConsoleTableView;
import com.example.market.core.controller.TableController;
import com.example.market.core.data.Repository;
import com.example.market.core.module.Module;
import com.example.market.core.module.TableModule;

public class Application {

    public static void main(String[] args) {
        final Repository<Product> mInMemoryStorage = new InMemoryStorage<>();
        final Module<?> tableModule = new TableModule<Product>(mInMemoryStorage, TableController.newInstance(Product::new), new ConsoleTableView<>());
        tableModule.run();
    }
}
