package com.company.texteditor;

import com.company.texteditor.dbManager.User;
import com.company.texteditor.login.Login;
import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.strategy_theme.Theme;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public CardLayout cl;

    public Panel(LanguageSubject languageSubject, MainFrame mainFrame, Theme theme) {
        setLayout(new CardLayout());
        add(new Login(languageSubject, mainFrame, theme), "login");
        cl = (CardLayout) getLayout();
        cl.show(this, "login");
    }

    public Panel(LanguageSubject languageSubject, MainFrame mainFrame, Theme theme, User user) {
        setLayout(new CardLayout());
        add(new MainArea(languageSubject, mainFrame, theme, user), "mainArea");
        cl = (CardLayout) getLayout();
        cl.show(this, "mainArea");
    }
}
