package com.company.texteditor.dbManager;

public class ChangeUserLan extends DBTemplate {
    private String language;

    public ChangeUserLan(int userId, String language) {
        this.userID = userId;
        this.language = language;
    }

    @Override
    public void findUserFromDB(int id) {
        user = em.find(User.class, id);
    }

    @Override
    public void queryDB() {
        user.setCurrent_language(language);
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
