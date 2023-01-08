package com.company.texteditor.dbManager;

import javax.persistence.TypedQuery;
import java.util.List;

public class GetUserList extends DBTemplate {

    @Override
    public void queryDB() {
        TypedQuery<User> query = em.createQuery("SELECT user FROM User user", User.class);
        this.userList = query.getResultList();
    }

}
