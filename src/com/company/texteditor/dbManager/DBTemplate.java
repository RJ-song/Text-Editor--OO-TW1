package com.company.texteditor.dbManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class DBTemplate {
    protected EntityManagerFactory emf;
    protected EntityManager em;
    protected List<User> userList = null;
    protected int userID;
    protected User user;

    final public void execute() {
        startDB();
        // 是否需要更改user資料
        if (isNeedChangeUser()) {
            findUserFromDB(userID);
        }
        connectDB();
        queryDB();
        // 是否修改過資料
        if (isChangeData()) {
            update();
        }
        disconnectDB();
    }

    public void findUserFromDB(int id) {
        System.out.println("nothing to do");
    }

    public void startDB() {
        this.emf = Persistence.createEntityManagerFactory("objectdb:db/user.odb");
        this.em = emf.createEntityManager();
    }

    public void connectDB() {
        em.getTransaction().begin();
    }

    public abstract void queryDB();

    public void update() {
        em.getTransaction().commit();
    }

    public boolean isNeedChangeUser() {
        return false;
    }

    public boolean isChangeData() {
        return false;
    }

    public void disconnectDB() {
        em.close();
        emf.close();
    }

    public List<User> getUserList() {
        return userList;
    }

}
