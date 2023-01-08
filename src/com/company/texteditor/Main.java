package com.company.texteditor;

import com.company.texteditor.observer.LanguageSubject;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LanguageSubject languageSubject = new LanguageSubject();

                MainWindow mainWindow = new MainWindow(languageSubject);
            }
        });

    }
}
