package com.company.texteditor;

import com.company.texteditor.dbManager.User;
import com.company.texteditor.login.Login;
import com.company.texteditor.decorator.*;
import com.company.texteditor.factory.Functions;
import com.company.texteditor.factory.Select_Function;
import com.company.texteditor.iterator.IteratorMain;
import com.company.texteditor.login.Register;
import com.company.texteditor.memento.Caretaker;
import com.company.texteditor.memento.Originator;
import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.state.Context;
import com.company.texteditor.strategy_language.Chinese;
import com.company.texteditor.strategy_language.English;
import com.company.texteditor.strategy_language.Language;
import com.company.texteditor.strategy_theme.*;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainWindow {

    // strategy theme
    private Theme theme;

    private MainFrame mainFrame;

    public MainWindow(LanguageSubject languageSubject) {

        mainFrame = new MainFrame(languageSubject);

        // create strategy theme context
        theme = new Theme();
        theme.performWindow();

        Panel panel = new Panel(languageSubject, mainFrame, theme);
        mainFrame.add(panel, BorderLayout.CENTER);
        show();
    }

    public MainWindow(LanguageSubject languageSubject, User user) {

        mainFrame = new MainFrame(languageSubject);

        // create strategy theme context
        theme = new Theme();
        theme.performWindow();

        Panel panel = new Panel(languageSubject, mainFrame, theme, user);
        mainFrame.add(panel, BorderLayout.CENTER);
        show();
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
