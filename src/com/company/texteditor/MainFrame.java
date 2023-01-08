package com.company.texteditor;

import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;

public class MainFrame extends JFrame implements Observer {
    LanguageSubject languageSubject;
    TextStorage textStorage;

    public MainFrame(LanguageSubject languageSubject) {
        // register observer
        this.languageSubject = languageSubject;
        languageSubject.registerObserver(this);
        textStorage = languageSubject.getTextStorage();

        setTitle(textStorage.getText("Text Editor"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("src/llama.png");
        setIconImage(image.getImage());
    }

    @Override
    public void update() {
        textStorage = languageSubject.getTextStorage();
        setTitle(textStorage.getText("Text Editor"));
    }
}
