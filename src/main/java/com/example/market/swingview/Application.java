package com.example.market.swingview;

import com.example.market.core.data.Repository;
import com.example.market.core.data.XmlRepository;
import com.example.market.core.model.Model;
import com.example.market.core.module.TableTableModule;
import com.example.market.core.viewmodel.TableViewModel;
import com.example.market.swingview.model.Employee;
import com.example.market.swingview.model.Product;
import com.example.market.swingview.model.Shop;
import com.example.market.swingview.view.SwingTableView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class Application {

    private final Class<? extends Model<?>>[] modelClasses;

    Application(Class<? extends Model<?>>... modelClasses) {
        this.modelClasses = modelClasses;
    }

    public static void main(String[] args) {
        new Application(Shop.class, Employee.class, Product.class).run();
    }

    private void run() {
        final JFrame frame = new JFrame("My app");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTabbedPane tabs = new JTabbedPane();
        addTabs(tabs, modelClasses);
        frame.add(tabs);
        frame.setSize(new Dimension(700, 500));
        frame.setLocation(200, 100);
        frame.setVisible(true);
    }

    private void addTabs(JTabbedPane tabs, Class<? extends Model<?>>[] modelClasses) {
        Arrays.stream(modelClasses)
                .forEach(modelClass -> {
                    Supplier<Model> modelFactory = () -> newInstance(modelClass);
                    final var tableModule = createTableModule(modelFactory);
                    addTab(tabs, tableModule);
                });
    }

    private Model<?> newInstance(Class<? extends Model<?>> modelClass) {
        try {
            return modelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Can't instantiate " + modelClass.getName());
        }
    }

    private <M extends Model<M>> void addTab(JTabbedPane tabs, TableTableModule<M, SwingTableView<M>, TableViewModel<M>, Repository<M>> module) {
        tabs.addTab(module.getModel().getDisplayName(), module.getView());
    }

    private <T extends Model<T>> TableTableModule<T, SwingTableView<T>, TableViewModel<T>, Repository<T>> createTableModule(Supplier<T> modelFactory) {
        final var fileName = modelFactory.get().getName().toLowerCase() + ".xml";
        final SwingTableView<T> tableView = new SwingTableView<>();
        final TableViewModel<T> viewModel = TableViewModel.newInstance(modelFactory);
        final Repository<T> repository = new XmlRepository<>(Path.of(fileName).toFile(), modelFactory);
        return new TableTableModule<>(repository, viewModel, tableView);
    }

}
