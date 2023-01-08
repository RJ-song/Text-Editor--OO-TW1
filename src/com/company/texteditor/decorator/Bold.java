package com.company.texteditor.decorator;

import javax.swing.*;
import javax.swing.text.*;

public class Bold extends Decorator {
    JTextPane textPane;
    StyledDocument doc;

    public Bold(Glyph g, JTextPane textPane) {
        this.glyph = g;
        this.textPane = textPane;
        doc = (StyledDocument) textPane.getDocument();
        changeStyle();
    }

    public void changeStyle() {
        boolean isAllBold = allIsBold();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        int start = textPane.getSelectionStart();
        int len = textPane.getSelectedText().length();

        if (isAllBold == true) {
            StyleConstants.setBold(attributeSet, false);
            doc.setCharacterAttributes(start, len, attributeSet, false);
        } else {
            StyleConstants.setBold(attributeSet, true);
            doc.setCharacterAttributes(start, len, attributeSet, false);
        }
    }
    private boolean allIsBold() {
        boolean isAllBold = true;
        int start = textPane.getSelectionStart();

        for (int i = start; i < start + textPane.getSelectedText().length(); i++) {
            Element element = doc.getCharacterElement(i);
            AttributeSet attributeSet = element.getAttributes();
            if (StyleConstants.isBold(attributeSet) != true) {
                isAllBold = false;
            }
        }

        return isAllBold;
    }

    @Override
    public String getDescription() {
        return this.description + ", Bold";
    }
}
