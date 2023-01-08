package com.company.texteditor.dbManager;

public class ChangeUserTheme extends DBTemplate {
    private String theme;

    public ChangeUserTheme(int userId, String theme) {
        this.userID = userId;
        this.theme = theme;
    }

    @Override
    public void findUserFromDB(int id) {
        user = em.find(User.class, id);
    }

    @Override
    public void queryDB() {
        user.setCurrent_theme(theme);
    }

    @Override
    public boolean isChangeData() {
        return true;
    }

    @Override
    public boolean isNeedChangeUser() {
        return true;
    }
}
