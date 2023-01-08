package com.company.texteditor.decorator;

import javax.swing.*;
import javax.swing.text.*;

public class Italic extends Decorator {
    JTextPane textPane;
    StyledDocument doc;

    public Italic(Glyph g, JTextPane textPane) {
        this.glyph = g;
        this.textPane=textPane;
        doc = (StyledDocument) textPane.getDocument();
        changeStyle();
    }

    public void changeStyle() {
        boolean isAllItalic = allIsItalic();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        int start = textPane.getSelectionStart();
        int len = textPane.getSelectedText().length();

        if (isAllItalic == true) {
            StyleConstants.setItalic(attributeSet, false);
            doc.setCharacterAttributes(start, len, attributeSet, false);
        } else {
            StyleConstants.setItalic(attributeSet, true);
            doc.setCharacterAttributes(start, len, attributeSet, false);
        }
    }
    private boolean allIsItalic() {
        boolean isAllItalic = true;
        int start = textPane.getSelectionStart();

        for (int i = start; i < start + textPane.getSelectedText().length(); i++) {
            Element element = doc.getCharacterElement(i);
            AttributeSet attributeSet = element.getAttributes();
            if (StyleConstants.isItalic(attributeSet) != true) {
                isAllItalic = false;
            }
        }
        return isAllItalic;
    }

    @Override
    public String getDescription() {
        return this.description + ", Italic";
    }
}
