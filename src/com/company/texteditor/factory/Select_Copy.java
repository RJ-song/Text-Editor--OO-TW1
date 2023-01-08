package com.company.texteditor.factory;

import javax.swing.*;

public class Select_Copy implements Select_Function{
    @Override
    public Functions fun(JTextPane text){
        return new Copy(text);
    }
}
