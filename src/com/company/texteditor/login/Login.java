package com.company.texteditor.login;

import com.company.texteditor.MainArea;
import com.company.texteditor.MainFrame;
import com.company.texteditor.Panel;
import com.company.texteditor.dbManager.DBTemplate;
import com.company.texteditor.dbManager.GetUserList;
import com.company.texteditor.dbManager.User;
import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.strategy_language.Chinese;
import com.company.texteditor.strategy_language.English;
import com.company.texteditor.strategy_language.Language;
import com.company.texteditor.strategy_theme.*;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Login extends JPanel implements ActionListener {
    private LanguageSubject languageSubject;
    private TextStorage textStorage;

    // strategy theme
    private Theme theme;

    private MainFrame mainFrame;

    //登入介面設置
    JLabel userL;
    JTextField userTF;
    JLabel passL;
    JPasswordField passTF;
    //佈局
    JPanel loginP;
    JPanel panel;
    //END
    JButton login;
    JButton register;

    public Login (LanguageSubject languageSubject, MainFrame mainFrame, Theme theme){
        mainFrame.setTitle("Text Editor");
        theme.setWindowTheme(new WhiteWindow());
        theme.performWindow();

        this.languageSubject = languageSubject;
        this.textStorage = languageSubject.getTextStorage();
        this.mainFrame = mainFrame;

        this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.theme = theme;

        userL = new JLabel("Username: ");
        userTF = new JTextField();
        passL = new JLabel("Password: ");
        passTF = new JPasswordField();
        loginP = new JPanel(new GridLayout(3,2));
        panel = new JPanel();
        login = new JButton("Login");
        register = new JButton("Register");

        setLayout(new CardLayout());
        loginP.add(userL);
        loginP.add(userTF);
        loginP.add(passL);
        loginP.add(passTF);
        login.addActionListener(this);      //Button 加監聽
        register.addActionListener(this);
        loginP.add(login);
        loginP.add(register);
        panel.add(loginP);
        add(panel, "login");
//        cl = (CardLayout) getLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String account = userTF.getText();
            String password = new String(passTF.getPassword());
            User userC = null;

            DBTemplate getUserList = new GetUserList();
            getUserList.execute();
            List<User> userList = getUserList.getUserList();

            for (User user : userList) {
                if (account.equals(user.getAccount())) {
                    userC = user;
                }
            }

            if (userC == null) {
                // warning message
                Object[] checkButton = {"OK"};
                Object defaultCheckButton = checkButton[0];
                ImageIcon icon = new ImageIcon("src/llama.png");
                Image image = icon.getImage(); // transform it
                Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);// transform it back
                JOptionPane.showOptionDialog(mainFrame,
                        "User does not exist.",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.INFORMATION_MESSAGE,
                        icon,
                        checkButton,
                        defaultCheckButton);
                return;
            } else {
                if (password.equals(userC.getPassword())) {
                    switch (userC.getCurrent_language()) {
                        case "English":
                            Language language = new Language();
                            language.setLanguageStrategy(new English());
                            languageSubject.changeLanguage(language);
                            break;
                        case "Chinese":
                            Language language1 = new Language();
                            language1.setLanguageStrategy(new Chinese());
                            languageSubject.changeLanguage(language1);
                            break;
                    }
                    switch (userC.getCurrent_theme()) {
                        case "White":
                            theme.setWindowTheme(new WhiteWindow());
                            break;
                        case "Dark":
                            theme.setWindowTheme(new DarkWindow());
                            break;
                        case "HighContrastWhite":
                            theme.setWindowTheme(new HighContrastWhiteWindow());
                            break;
                        case "HighContrastDark":
                            theme.setWindowTheme(new HighContrastDarkWindow());
                            break;
                    }
                    performMainPanel(userC);

                } else {
                    // warning message
                    Object[] checkButton = {"OK"};
                    Object defaultCheckButton = checkButton[0];
                    ImageIcon icon = new ImageIcon("src/llama.png");
                    Image image = icon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);// transform it back
                    JOptionPane.showOptionDialog(mainFrame,
                            "Password is incorrect",
                            "Warning",
                            JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.INFORMATION_MESSAGE,
                            icon,
                            checkButton,
                            defaultCheckButton);
                }
            }

        }

        if (e.getSource() == register) {
            performRegisterPanel();
        }
    }

    public void performMainPanel(User user) {
        Panel panel = (Panel) getParent();
        panel.add(new MainArea(languageSubject, mainFrame, theme, user), "mainArea");
        panel.cl.show(panel, "mainArea");
    }

    public void performRegisterPanel() {
        Panel panel = (Panel) getParent();
        panel.add(new Register(languageSubject, mainFrame, theme), "register");
        panel.cl.show(panel, "register");
    }
}
