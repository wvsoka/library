package com.lista2.service;

import java.sql.Date;

public class LoanDTO {
    private Integer id;

    private Date loanStartDate;
    private Date loanEndDate;
    private Date bookReturnDate;
    private Integer userLoan;
    private Integer bookLoan;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Date getBookReturnDate() {
        return bookReturnDate;
    }

    public void setBookReturnDate(Date bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }

    public Integer getUserLoan() {
        return userLoan;
    }

    public void setUserLoan(Integer userLoan) {
        this.userLoan = userLoan;
    }

    public Integer getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(Integer bookLoan) {
        this.bookLoan = bookLoan;
    }
}
