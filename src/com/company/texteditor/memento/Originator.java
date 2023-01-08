package com.company.texteditor.memento;

import javax.swing.text.AttributeSet;
import java.awt.*;
import java.util.List;

public class Originator {
    private String text;
    private List<AttributeSet> attributeSetList;
    private int textSize;
    private Color textColor;
    private String textFamily;

    public Memento storeInMemento(){
        System.out.println("Originator: Saving to Memento.");
        return new Memento(text, attributeSetList, textSize, textColor, textFamily);
    }

    public void restoreFromMemento(Memento memento){
        text = memento.getText();
        attributeSetList = memento.getAttributeSetList();
        textSize = memento.getTextSize();
        textColor = memento.getTextColor();
        textFamily = memento.getTextFamily();
        System.out.println("Originator: State after restoring from Memento");
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttributeSetList(List<AttributeSet> attributeSetList) {
        this.attributeSetList = attributeSetList;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setTextFamily(String textFamily) {
        this.textFamily = textFamily;
    }

    public String getText() {
        return text;
    }

    public List<AttributeSet> getAttributeSetList() {
        return attributeSetList;
    }

    public Color getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getTextFamily() {
        return textFamily;
    }
}
