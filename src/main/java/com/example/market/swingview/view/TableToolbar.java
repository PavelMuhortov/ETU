package com.example.market.swingview.view;

import javax.swing.*;

/**
 * Панель инструментов
 */
public class TableToolbar
        extends JToolBar {

    /**
     * Кнопка "Добавить". Добавляет новую строку.
     */
    private JButton add;

    /**
     * Кнопка "Удалить". Удаляет выделенные строки
     */
    private JButton delete;

    /**
     * Кнопка "Сохранить". Сохраняет изменения
     */
    private JButton save;

    /**
     * Кнопка "Обновить". Обновляет данные
     */
    private JButton refresh;

    public TableToolbar() {
        add = new JButton("Add");
        delete = new JButton("Delete");
        save = new JButton("Save");
        refresh = new JButton("Refresh");
        add(add);
        add(delete);
        add(save);
        add(refresh);
    }

    /**
     * Добавляет слушателя кнопки "Добавить"
     * @param buttonListener
     * @return
     */
    public TableToolbar onAdd(Runnable buttonListener) {
        add.addActionListener(e -> buttonListener.run());
        return this;
    }

    /**
     * Добавляет сушателя кнопки "Удалить"
     * @param buttonListener
     * @return
     */
    public TableToolbar onDelete(Runnable buttonListener) {
        delete.addActionListener(e -> buttonListener.run());
        return this;
    }

    /**
     * Добавляет сушателя кнопки "Сохранить"
     * @param buttonListener
     * @return
     */
    public TableToolbar onSave(Runnable buttonListener) {
        save.addActionListener(e -> buttonListener.run());
        return this;
    }

    /**
     * Добавляет сушателя кнопки "Обновить"
     * @param buttonListener
     * @return
     */
    public TableToolbar onRefresh(Runnable buttonListener) {
        refresh.addActionListener(e -> buttonListener.run());
        return this;
    }

}
