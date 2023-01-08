package com.company.texteditor.strategy_theme;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.HighContrastLightTheme;

import javax.swing.*;
import java.awt.*;

public class HighContrastWhiteWindow implements WindowColor {

    @Override
    public void setWindow() {
        LafManager.setTheme(new HighContrastLightTheme());
        LafManager.install();
    }
}