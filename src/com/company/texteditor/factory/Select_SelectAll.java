package com.company.texteditor.factory;

import javax.swing.*;

public class Select_SelectAll implements Select_Function{
    @Override
    public Functions fun(JTextPane text){
        return new SelectAll(text);
    }
}
