package com.company.texteditor.strategy_theme;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.IntelliJTheme;

import javax.swing.*;
import java.awt.*;

public class WhiteWindow implements WindowColor {

    @Override
    public void setWindow() {
        LafManager.setTheme(new IntelliJTheme());
        LafManager.install();
    }
}
