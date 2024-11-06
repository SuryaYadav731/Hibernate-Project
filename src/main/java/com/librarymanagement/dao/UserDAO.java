package com.librarymanagement.dao;

import com.librarymanagement.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private SessionFactory factory;

    public UserDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void addUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    public User getUser(String username) {
        Session session = factory.openSession();
        User user = session.createQuery("FROM User WHERE username = :username", User.class)
                           .setParameter("username", username)
                           .uniqueResult();
        session.close();
        return user;
    }

    public boolean authenticateUser(String username, String password) {
        User user = getUser(username);
        if (user != null) {
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }
}
