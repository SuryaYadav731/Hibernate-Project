package com.librarymanagement.dao;

import com.librarymanagement.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BookDAO {
    private SessionFactory factory;

    public BookDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void addBook(Book book) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(book);
        tx.commit();
        session.close();
    }

    public List<Book> getAllBooks() {
        Session session = factory.openSession();
        List<Book> books = session.createQuery("FROM Book", Book.class).list();
        session.close();
        return books;
    }

    public Book getBook(int bookId) {
        Session session = factory.openSession();
        Book book = session.get(Book.class, bookId);
        session.close();
        return book;
    }

    public void updateBook(Book book) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(book);
        tx.commit();
        session.close();
    }

    public void deleteBook(int bookId) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Book book = session.get(Book.class, bookId);
        if (book != null) {
            session.delete(book);
        }
        tx.commit();
        session.close();
    }
}
