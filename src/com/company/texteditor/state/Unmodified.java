package com.company.texteditor.state;

import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;

public class Unmodified extends State {
    private String unmodifiedText;

    public Unmodified(String unmodifiedText) {
        this.unmodifiedText = unmodifiedText;
    }

    @Override
    void Handle(JLabel stateBar){
        stateBar.setText(unmodifiedText);
    }
}