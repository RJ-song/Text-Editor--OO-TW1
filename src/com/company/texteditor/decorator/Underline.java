package com.company.texteditor.decorator;

import javax.swing.*;
import javax.swing.text.*;

public class Underline extends Decorator {
    JTextPane textPane;
    StyledDocument doc;

    public Underline(Glyph g, JTextPane textPane) {
        this.glyph = g;
        this.textPane=textPane;
        doc = (StyledDocument) textPane.getDocument();
        changeStyle();
    }

    public void changeStyle() {
        boolean isAllUnderline = allIsUnderline();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        int start = textPane.getSelectionStart();
        int len = textPane.getSelectedText().length();

        if (isAllUnderline == true) {
            StyleConstants.setUnderline(attributeSet, false);
            doc.setCharacterAttributes(start, len, attributeSet, false);
        } else {
            StyleConstants.setUnderline(attributeSet, true);
            doc.setCharacterAttributes(start, len, attributeSet, false);
        }
    }
    private boolean allIsUnderline() {
        boolean isAllUnderline = true;
        int start = textPane.getSelectionStart();

        for (int i = start; i < start + textPane.getSelectedText().length(); i++) {
            Element element = doc.getCharacterElement(i);
            AttributeSet attributeSet = element.getAttributes();
            if (StyleConstants.isUnderline(attributeSet) != true) {
                isAllUnderline = false;
            }
        }

        return isAllUnderline;
    }

    @Override
    public String getDescription() {
        return this.description + ", Underline";
    }
}


