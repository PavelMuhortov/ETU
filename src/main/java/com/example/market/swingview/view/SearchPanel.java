package com.example.market.swingview.view;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * Панель поиска по имени колонки
 */
public class SearchPanel extends JPanel {

    public static final String DEFAULT_TEXT = "Введите текст";
    private final JComboBox<String> filterProperty;
    private final JTextField filterValue;
    private final JButton search;
    private final JButton reset;
    private final List<String> propertyNames;
    private final List<String> propertyDisplayNames;

    public SearchPanel(List<String> propertyNames, List<String> propertyDisplayNames) {
        this.propertyNames = propertyNames;
        this.propertyDisplayNames = propertyDisplayNames;
        filterProperty = new JComboBox<>(propertyDisplayNames.toArray(new String[0]));
        filterValue = new JTextField(DEFAULT_TEXT, 20);
        filterValue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (filterValue.getText().equals(DEFAULT_TEXT))
                    filterValue.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (filterValue.getText().isBlank())
                    filterValue.setText(DEFAULT_TEXT);
            }
        });
        search = new JButton("Искать");
        reset = new JButton("Очистить");
        add(filterProperty);
        add(filterValue);
        add(search);
        add(reset);
    }

    /**
     * Добавляет слушателя кнопки поиск
     * @param consumer
     */
    public void onFilter(Consumer<FilterOptions> consumer) {
        search.addActionListener(event -> {
            String property = (String) filterProperty.getSelectedItem();
            int index = propertyDisplayNames.indexOf(property);
            String propertyName = propertyNames.get(index);
            String value = filterValue.getText();
            consumer.accept(new FilterOptions(propertyName, value));
        });
    }

    public void onReset(Runnable listener) {
        reset.addActionListener(e -> {
            filterValue.setText(DEFAULT_TEXT);
            listener.run();
        });
    }

    /**
     * Данные поиска
     */
    public static class FilterOptions {
        /**
         * имя поля
         */
        private final String property;
        /**
         * значение поля
         */
        private final String value;

        public FilterOptions(String property, String value) {
            this.property = property;
            this.value = value;
        }

        public String getProperty() {
            return property;
        }

        public String getValue() {
            return value;
        }
    }

}
