package com.company.texteditor.factory;

import javax.swing.*;

public class Select_Cut implements Select_Function{
    @Override
    public Functions fun(JTextPane text){
        return new Cut(text);
    }
}