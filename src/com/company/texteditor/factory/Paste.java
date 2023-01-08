package com.company.texteditor.factory;

import javax.swing.*;

public class Paste implements Functions{
    private JTextPane text;

    Paste(JTextPane text){
        this.text = text;
    }
    @Override
    public void getType() {
        text.paste();
    }
}
