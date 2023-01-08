package com.company.texteditor.factory;

import javax.swing.*;

public class SelectAll implements Functions{
    private JTextPane text;

    SelectAll(JTextPane text){
        this.text = text;
    }
    @Override
    public void getType() {
        text.selectAll();
    }
}
