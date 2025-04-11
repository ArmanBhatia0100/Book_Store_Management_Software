/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author arman
 */
public class Book {

    public enum Status {
        AVAILABLE,
        ISSUED
    }

    private String author;
    private String title;
    private String isbn;
    private Status status;
    private LocalDate dated_date;

    public String getAuthor() {
        return author;
    }

    public Book( String title,String author, String isbn, Status status, LocalDate dated_date) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.status = status;
        this.dated_date = dated_date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDated_date() {
        return dated_date;
    }

    public void setDated_date(LocalDate dated_date) {
        this.dated_date = dated_date;
    }

    @Override
    public String toString() {
        return "Book{" + "author=" + author + ", title=" + title + ", isbn=" + isbn + ", status=" + status + ", dated_date=" + dated_date + '}';
    }

}
