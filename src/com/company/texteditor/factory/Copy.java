package com.company.texteditor.factory;

import javax.swing.*;

public  class Copy implements Functions{
    private JTextPane text;

    Copy(JTextPane text){
        this.text = text;
    }
    @Override
    public void getType() {
        text.copy();
    }
}
