package com.company.texteditor.factory;

import javax.swing.*;

public class Cut implements Functions{
    private JTextPane text;

    Cut(JTextPane text){
        this.text = text;
    }
    @Override
    public void getType() {
        text.cut();
    }
}