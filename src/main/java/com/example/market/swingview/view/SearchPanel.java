package com.example.market.swingview.view;

import com.example.market.core.model.PropDef;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * Панель поиска по имени колонки
 */
public class SearchPanel extends JPanel {

    public static final String DEFAULT_TEXT = "Enter text     ";
    private final JComboBox<String> filterProperty;
    private final JTextField filterValue;
    private final JButton search;
    private final List<PropDef> propDefs;

    public SearchPanel(List<PropDef> propDefs) {
        this.propDefs = propDefs;
        String[] propertyNames = this.propDefs.stream()
                .map(PropDef::getPropertyDisplayedName)
                .toArray(String[]::new);
        filterProperty = new JComboBox<>(propertyNames);
        filterValue = new JTextField(DEFAULT_TEXT);
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
        search = new JButton("Search");
        add(filterProperty);
        add(filterValue);
        add(search);
    }

    /**
     * Добавляет слушателя кнопки поиск
     * @param consumer
     */
    public void onFilter(Consumer<FilterOptions> consumer) {
        search.addActionListener(event -> {
            String property = (String) filterProperty.getSelectedItem();
            String propertyName = propDefs.stream()
                    .filter(propDef -> propDef.getPropertyDisplayedName().equals(property))
                    .map(PropDef::getPropertyName)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Can't find property name for " + property));
            String value = filterValue.getText();
            consumer.accept(new FilterOptions(propertyName, value));
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
