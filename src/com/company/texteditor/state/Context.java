package com.company.texteditor.state;

import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;

public class Context implements Observer {
    private State state;
    public JLabel stateBar;
    private LanguageSubject languageSubject;
    private TextStorage textStorage;

    public Context(LanguageSubject languageSubject){
        // register observer
        this.languageSubject = languageSubject;
        languageSubject.registerObserver(this);
        textStorage = languageSubject.getTextStorage();

        this.state = new Unmodified(textStorage.getText("Unmodified"));
        stateBar = new JLabel(textStorage.getText("Unmodified"));
    }
    public void initial(){stateBar.setText(textStorage.getText("Unmodified"));}
    public void setState(State state){this.state = state;}
    public String getState(){return state.getClass().getName();}
    public void Handle(boolean b){
        if(b){
            this.state = new Modified(textStorage.getText("Modified"));
        }
        else{
            this.state = new Unmodified(textStorage.getText("Unmodified"));
        }
        state.Handle(stateBar);
    }

    @Override
    public void update() {
        textStorage = languageSubject.getTextStorage();
        if (state.getClass().getName() == "com.company.texteditor.state.Modified") {
            stateBar.setText(textStorage.getText("Modified"));
        } else {
            stateBar.setText(textStorage.getText("Unmodified"));
        }
    }
}
