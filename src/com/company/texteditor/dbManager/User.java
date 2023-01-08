package com.company.texteditor.dbManager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id @GeneratedValue
    private int id;
    private String account;
    private String password;
    private String current_theme;
    private String current_language;

    public User() {

    }

    public User(String account ,String password, String current_theme, String current_language) {
        this.account = account;
        this.password = password;
        this.current_theme = current_theme;
        this.current_language = current_language;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getCurrent_theme() {
        return current_theme;
    }

    public String getCurrent_language() {
        return current_language;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurrent_theme(String current_theme) {
        this.current_theme = current_theme;
    }

    public void setCurrent_language(String current_language) {
        this.current_language = current_language;
    }
}

