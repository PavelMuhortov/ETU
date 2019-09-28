package com.example.market.swingview.view;

import javax.swing.*;

public class TableToolbar
        extends JToolBar {

    private JButton add;

    private JButton delete;

    private JButton save;

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

    public TableToolbar onAdd(Runnable buttonListener) {
        add.addActionListener(e -> buttonListener.run());
        return this;
    }

    public TableToolbar onDelete(Runnable buttonListener) {
        delete.addActionListener(e -> buttonListener.run());
        return this;
    }

    public TableToolbar onSave(Runnable buttonListener) {
        save.addActionListener(e -> buttonListener.run());
        return this;
    }

    public TableToolbar onRefresh(Runnable buttonListener) {
        refresh.addActionListener(e -> buttonListener.run());
        return this;
    }

}
