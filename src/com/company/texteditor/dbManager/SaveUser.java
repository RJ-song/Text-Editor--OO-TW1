package com.company.texteditor.dbManager;

public class SaveUser extends DBTemplate {
    private User newUser;

    public SaveUser(User newUser) {
        this.newUser = newUser;
    }

    @Override
    public void queryDB() {
        em.persist(newUser);
    }

    @Override
    public boolean isChangeData() {
        return true;
    }
}
