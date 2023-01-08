package com.company.texteditor.strategy_theme;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.HighContrastDarkTheme;

import javax.swing.*;
import java.awt.*;

public class HighContrastDarkWindow implements WindowColor {

    @Override
    public void setWindow() {
        LafManager.setTheme(new HighContrastDarkTheme());
        LafManager.install();
    }
}
