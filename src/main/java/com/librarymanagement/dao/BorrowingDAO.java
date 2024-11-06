package com.librarymanagement.dao;

import com.librarymanagement.model.Borrowing;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BorrowingDAO {
    private SessionFactory factory;

    public BorrowingDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void borrowBook(Borrowing borrowing) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(borrowing);
        tx.commit();
        session.close();
    }

    public void returnBook(Borrowing borrowing) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        borrowing.setReturnDate(new java.util.Date());
        session.update(borrowing);
        tx.commit();
        session.close();
    }

    public Borrowing getBorrowing(int userId, int bookId) {
        Session session = factory.openSession();
        Borrowing borrowing = session.createQuery("FROM Borrowing WHERE user.id = :userId AND book.id = :bookId", Borrowing.class)
                .setParameter("userId", userId)
                .setParameter("bookId", bookId)
                .uniqueResult();
        session.close();
        return borrowing;
    }
}
