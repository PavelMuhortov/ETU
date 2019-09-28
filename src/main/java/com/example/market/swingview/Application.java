package com.example.market.swingview;

import com.example.market.consoleview.model.InMemoryStorage;
import com.example.market.core.controller.TableController;
import com.example.market.core.data.Repository;
import com.example.market.core.module.Module;
import com.example.market.core.module.TableModule;
import com.example.market.swingview.model.Product;
import com.example.market.swingview.view.SwingTableView;

import javax.swing.*;
import java.awt.*;

public class Application {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("My app");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final SwingTableView<Product> tableView = new SwingTableView<>();
        frame.add(tableView);
        frame.setSize(new Dimension(500, 500));
        frame.setLocation(200, 100);
        createProductTableModule(tableView);
        frame.setVisible(true);
    }

    private static void createProductTableModule(SwingTableView<Product> tableView) {
        final TableController<Product> controller = TableController.newInstance(Product.class);
        final Repository<Product> repository = new InMemoryStorage<>();
        repository.save(new Product("Orange", "Fruit", 7, "Кг"));
        repository.save(new Product("Apple", "Fruit", 7, "Кг"));
        final Module<?> tableModule = new TableModule<Product>(repository, controller, tableView);
        tableModule.run();
    }

}
