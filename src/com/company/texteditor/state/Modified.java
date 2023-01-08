package com.company.texteditor.state;

import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;

public class Modified extends State {
    private String modifiedText;

    public Modified(String modifiedText) {
        this.modifiedText = modifiedText;
    }

    @Override
    void Handle(JLabel stateBar){
        stateBar.setText(modifiedText);
    }
}

