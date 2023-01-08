package com.company.texteditor.decorator;

import javax.swing.text.SimpleAttributeSet;

public abstract class Glyph {
    SimpleAttributeSet attributeSet = new SimpleAttributeSet();
    String description = "Unknown Glyph";

    public String getDescription() {
        return description;
    }

    public abstract void changeStyle();
}
