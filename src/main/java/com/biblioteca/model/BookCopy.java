package com.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "book_copy")
public class BookCopy implements Serializable {

    private Long id;
    private Book book;
    private Set<Loan> loans;
    private Set<Reservation> reservations;

    public BookCopy() {
        super();
    }

    public BookCopy(Book book) {
        super();
        this.book = book;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //id autoincrementale
    @Column(name = "book_copy_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "bookBookCopyReference")
    @JoinColumn(name = "isbn")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @OneToMany(mappedBy = "bookCopy")
    @JsonManagedReference(value = "bookcopyLoanReference")
    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    @OneToMany(mappedBy = "bookCopy")
    @JsonManagedReference(value = "bookcopyReservationReference")
    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "id=" + id +
                ", book=" + book.toString() +
                '}';
    }
}
