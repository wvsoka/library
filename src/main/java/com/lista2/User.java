package com.lista2;

import jakarta.persistence.*;

import java.util.List;


/**
 * Class representing a user (librarian or reader) in the library system.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String login;
    private String password;
    private String role;
    private String eMail;
    private String fullName;

    @OneToMany(mappedBy = "userLoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> bookLoanList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Loan> getBookLoanList() {
        return bookLoanList;
    }

    public void setBookLoanList(List<Loan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }
}
