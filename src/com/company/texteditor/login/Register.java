package com.company.texteditor.login;

import com.company.texteditor.MainFrame;
import com.company.texteditor.Panel;
import com.company.texteditor.dbManager.*;
import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.strategy_theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Register extends JPanel implements ActionListener {
    private LanguageSubject languageSubject;

    private MainFrame mainFrame;
    private Theme theme;

    JLabel userL;
    JTextField userTF;
    JLabel passL;
    JPasswordField passTF;
    JLabel passLC;
    JPasswordField passC;
    JButton register;
    JButton back;

    public Register(LanguageSubject languageSubject, MainFrame mainFrame, Theme theme){
        this.languageSubject = languageSubject;
        this.mainFrame = mainFrame;
        this.theme = theme;

        userL = new JLabel("Create a Username: ");
        userTF = new JTextField();
        passL = new JLabel("Password: ");
        passTF = new JPasswordField();
        passLC = new JLabel("Confirm Password: ");
        passC = new JPasswordField();
        register = new JButton("Register");
        back = new JButton("Back");

        JPanel registerP = new JPanel();
        registerP.setLayout(new GridLayout(4,2));
        registerP.add(userL);
        registerP.add(userTF);
        registerP.add(passL);
        registerP.add(passTF);
        registerP.add(passLC);
        registerP.add(passC);
        registerP.add(register);
        registerP.add(back);
        register.addActionListener(this);
        back.addActionListener(this);
        add(registerP);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == register && passTF.getPassword().length>0 && userTF.getText().length()>0) {
            String pass = new String(passTF.getPassword());
            String confirm = new String(passC.getPassword());

            if (pass.equals(confirm)) {
                DBTemplate getUserList = new GetUserList();
                getUserList.execute();
                List<User> userList = getUserList.getUserList();
                //比對資料庫的使用者資料
                for (User user : userList) {
                    if (userTF.getText().equals(user.getAccount())) {
                        // warning message
                        Object[] checkButton = {"OK"};
                        Object defaultCheckButton = checkButton[0];
                        ImageIcon icon = new ImageIcon("src/llama.png");
                        Image image = icon.getImage(); // transform it
                        Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newimg);// transform it back
                        JOptionPane.showOptionDialog(mainFrame,
                                "User already exists.",
                                "Warning",
                                JOptionPane.INFORMATION_MESSAGE,
                                JOptionPane.INFORMATION_MESSAGE,
                                icon,
                                checkButton,
                                defaultCheckButton);

                        return;
                    }
                }

                User newUser = new User(userTF.getText(), new String(passTF.getPassword()), "White", "English");

                DBTemplate saveUser = new SaveUser(newUser);
                saveUser.execute();

                performLoginPanel();
            } else {
                // warning message
                Object[] checkButton = {"OK"};
                Object defaultCheckButton = checkButton[0];
                ImageIcon icon = new ImageIcon("src/llama.png");
                Image image = icon.getImage(); // transform it
                Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);// transform it back
                JOptionPane.showOptionDialog(mainFrame,
                        "Please check your confirm password.",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.INFORMATION_MESSAGE,
                        icon,
                        checkButton,
                        defaultCheckButton);
            }
        }

        if (e.getSource() == back){
            performLoginPanel();
        }
    }

    public void performLoginPanel() {
        Panel panel = (Panel) getParent();
        panel.add(new Login(languageSubject, mainFrame, theme), "login");
        panel.cl.show(panel, "login");
    }
}
