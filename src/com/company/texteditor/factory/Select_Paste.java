package com.company.texteditor.factory;

import javax.swing.*;

public class Select_Paste implements Select_Function{
    @Override
    public Functions fun(JTextPane text){
        return new Paste(text);
    }
}
