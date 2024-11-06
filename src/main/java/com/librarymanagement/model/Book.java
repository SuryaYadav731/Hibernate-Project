package com.librarymanagement.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private java.sql.Date publishedDate;
    private int availableCopies;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int id, String title, String author, Date publishedDate, int availableCopies) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishedDate = publishedDate;
		this.availableCopies = availableCopies;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public java.sql.Date getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(java.sql.Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	public int getAvailableCopies() {
		return availableCopies;
	}
	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}

    // Getters and Setters
    
}
