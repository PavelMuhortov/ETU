package com.example.market.swingview.view;

import com.example.market.core.controller.TableController;
import com.example.market.core.data.DataSupplier;
import com.example.market.core.model.Model;
import com.example.market.core.model.PropDef;
import com.example.market.core.view.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Отображение данных в табличном виде
 * @param <M>
 */
public class SwingTableView<M extends Model<M>>
        extends JPanel
        implements TableView<M> {

    private static final Logger LOG = LoggerFactory.getLogger(SwingTableView.class);

    private List<String> propertyNames;

    private TableController<M> controller;

    private DataSupplier<M> dataSupplier;

    private DefaultTableModel tableModel;

    private JTable table;

    /**
     * Инициализирует компоненты отображения
     */
    @SuppressWarnings("deprecation")
    @Override
    public void show() {
        List<PropDef> props = controller.newOne().getPropDefs();
        propertyNames = props.stream()
                .map(PropDef::getPropertyName)
                .collect(Collectors.toList());
        setLayout(new BorderLayout());
        TableToolbar toolBar = new TableToolbar();
        add(toolBar, BorderLayout.NORTH);
        SearchPanel searchPanel = new SearchPanel(props);
        setSearchPanelListeners(searchPanel);
        add(searchPanel, BorderLayout.SOUTH);
        String[] propertyNames = Stream.concat(Stream.of("Id"), props.stream()
                .map(PropDef::getPropertyDisplayedName))
                .toArray(String[]::new);
        tableModel = new DefaultTableModel(propertyNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table = new JTable(tableModel);
        setToolbarListeners(toolBar);
        Box contents = new Box(BoxLayout.Y_AXIS);
        add(contents, BorderLayout.CENTER);
        contents.add(new JScrollPane(table));
        fillTable();
    }

    /**
     * Устанавливает слушателей панели поиска
     * @param searchPanel
     */
    private void setSearchPanelListeners(SearchPanel searchPanel) {
        searchPanel.onFilter(filterOptions -> fillTable(dataSupplier.findBy(filterOptions.getProperty(), filterOptions.getValue())));
    }

    /**
     * Устанавливает слушателей панели инструментов
     * @param toolBar
     */
    private void setToolbarListeners(TableToolbar toolBar) {
        toolBar.onDelete(() -> {
            final int[] rows = table.getSelectedRows();
            LOG.debug("Selected rows: {}", Arrays.toString(rows));
            for (int i = 0; i < rows.length; i++) {
                long modelId = (long) tableModel.getValueAt(i, 0);
                controller.delete(modelId);
                tableModel.removeRow(rows[i] - i);
            }
        })
                .onAdd(() -> {
                    LOG.debug("Add entry event received");
                    final M model = controller.newOne();
                    addNewRow(model);
                })
                .onSave(() -> {
                    save();
                    fillTable();
                })
                .onRefresh(this::fillTable);
    }

    /**
     * Заполняет отображение данными
     */
    private void fillTable() {
        fillTable(dataSupplier.getAll());
    }

    @Override
    public void setController(TableController<M> controller) {
        this.controller = controller;
    }

    @Override
    public void setDataSupplier(DataSupplier<M> dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    /**
     * сохраняет измененные данные
     */
    private void save() {
        int rows = table.getModel().getRowCount();
        for (int i = 0; i < rows; i++) {
            long id = (long) tableModel.getValueAt(i, 0);
            M model = controller.newOne();
            model.setId(id);
            for (int j = 0; j < propertyNames.size(); j++) {
                String propertyName = propertyNames.get(j);
                String valueAt = (String) tableModel.getValueAt(i, j + 1);
                model.setPropertyValue(propertyName, valueAt);
            }
            LOG.debug("Saving data {}", model.toString());
            controller.save(model);
        }
    }

    /**
     * заполняет отображение данными
     * @param data
     */
    private void fillTable(Collection<M> data) {
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();
        data.forEach(this::addNewRow);
    }

    /**
     * добавляет новую строку в таблицу
     * @param model
     */
    private void addNewRow(M model) {
        final Object[] values = Stream.concat(Stream.of(model.getId()), propertyNames.stream()
                .map(model::getPropertyValue))
                .toArray();
        tableModel.addRow(values);
    }

}
