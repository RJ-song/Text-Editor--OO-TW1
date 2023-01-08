package com.company.texteditor.strategy_theme;

public class Theme {
    protected WindowColor windowColor;

    public Theme() {
        windowColor = new WhiteWindow();
    }

    public void setWindowTheme(WindowColor windowColor) {
        this.windowColor = windowColor;
    }

    public void performWindow() {
        windowColor.setWindow();
    }

    public WindowColor getWindowColor() {
        return windowColor;
    }
}


