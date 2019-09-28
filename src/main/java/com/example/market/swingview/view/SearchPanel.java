package com.example.market.swingview.view;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;

public class SearchPanel extends JPanel {


    private final JComboBox<String> filterProperty;
    private final JTextField filterValue;
    private final JButton search;
    private final JComboBox<String> sortBy;
    private final JButton sort;

    public SearchPanel(String[] propertyNames) {
        filterProperty = new JComboBox<>(propertyNames);
        String defaultText = "Enter text";
        filterValue = new JTextField(defaultText);
        filterValue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (filterValue.getText().equals(defaultText))
                    filterValue.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (filterValue.getText().isBlank())
                    filterValue.setText(defaultText);
            }
        });
        search = new JButton("Search");
        JLabel sortByLabel = new JLabel("Sort by");
        sortBy = new JComboBox<>(propertyNames);
        sort = new JButton("Sort");
        add(filterProperty);
        add(filterValue);
        add(search);
//        add(sortByLabel);
//        add(sortBy);
//        add(sort);
    }

    public void onFilter(Consumer<FilterOptions> consumer) {
        search.addActionListener(event -> {
            String property = (String) filterProperty.getSelectedItem();
            String value = filterValue.getText();
            consumer.accept(new FilterOptions(property, value));
        });
    }

    public void onSort(Consumer<String> consumer) {
        sort.addActionListener(event -> {
            String property = (String) sortBy.getSelectedItem();
            consumer.accept(property);
        });
    }

    public static class FilterOptions {
        private final String property;
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
