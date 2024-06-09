package com.lista2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Class representing a book in the library system.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Integer publishYear;
    private Integer availableCopies;

    @JsonIgnore
    @OneToMany(mappedBy = "bookLoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> bookLoanList;

    @JsonIgnore
    @OneToMany(mappedBy = "bookOpinion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Opinion> opinionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<Loan> getBookLoanList() {
        return bookLoanList;
    }

    public void setBookLoanList(List<Loan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }

    public List<Opinion> getOpinionList() {
        return opinionList;
    }

    public void setOpinionList(List<Opinion> opinionList) {
        this.opinionList = opinionList;
    }
}
