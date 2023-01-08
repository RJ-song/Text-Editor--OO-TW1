package com.company.texteditor.memento;

import javax.swing.text.AttributeSet;
import java.awt.*;
import java.util.List;

public class Memento {
    private String text;
    private List<AttributeSet> attributeSetList;
    private int textSize;
    private Color textColor;
    private String textFamily;

    public Memento(String text, List<AttributeSet> attributeSetList, int textSize, Color textColor, String textFamily){
        this.text = text;
        this.attributeSetList = attributeSetList;
        this.textSize = textSize;
        this.textColor = textColor;
        this.textFamily = textFamily;
    }

    public String getText() {
        return text;
    }

    public List<AttributeSet> getAttributeSetList() {
        return attributeSetList;
    }

    public int getTextSize() {
        return textSize;
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getTextFamily() {
        return textFamily;
    }
}
