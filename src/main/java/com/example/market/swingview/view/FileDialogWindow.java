package com.example.market.swingview.view;

import java.awt.*;

public class FileDialogWindow {

    private final String label;
    private final Mode mode;

    public FileDialogWindow(String label, Mode mode) {
        this.label = label;
        this.mode = mode;
    }

    public String getFilePath() {
        FileDialog fileDialog = new FileDialog(new Frame(), label, mode == Mode.LOAD ? 0 : 1);
        fileDialog.setVisible(true);
        return fileDialog.getDirectory() + fileDialog.getFile();
    }

    public enum Mode {
        LOAD, SAVE
    }
}
