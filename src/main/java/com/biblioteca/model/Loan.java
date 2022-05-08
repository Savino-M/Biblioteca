package com.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "loan")
public class Loan implements Serializable {

    private Long id;
    private BookCopy bookCopy;
    private User user;
    private Date startDate;
    private Date endDate;
    private LoanState loanState;

    public Loan() {
        super();
    }

    public Loan(BookCopy bookCopy, User user, Date startDate, Date endDate, LoanState loanState) {
        super();
        this.bookCopy = bookCopy;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loanState = loanState;
    }

    public enum LoanState {
        OPEN,
        CLOSED,
        EXTENDED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "bookcopyLoanReference")
    @JoinColumn(name = "book_copy_id")
    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "userLoanReference")
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "loan_start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "loan_end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "loan_state")
    public LoanState getLoanState() {
        return loanState;
    }

    public void setLoanState(LoanState loanState) {
        this.loanState = loanState;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", bookCopy=" + bookCopy.getId() +
                ", user=" + user.getId() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", loanState=" + loanState +
                '}';
    }
}
