package com.company.texteditor.strategy_theme;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.OneDarkTheme;

import javax.swing.*;
import java.awt.*;

public class DarkWindow implements WindowColor {

    @Override
    public void setWindow() {
        LafManager.setTheme(new OneDarkTheme());
        LafManager.install();
    }
}
