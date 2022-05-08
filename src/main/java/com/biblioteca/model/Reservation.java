package com.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private Long id;
    private BookCopy bookCopy;
    private User user;
    private Date startDate;
    private Date endDate;
    private ReservationState state;

    public Reservation() {
        super();
    }


    public Reservation(BookCopy bookCopy, User user, Date startDate, Date endDate, ReservationState state) {
        super();
        this.bookCopy = bookCopy;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "bookcopyReservationReference")
    @JoinColumn(name = "book_copy_id")
    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "userReservationReference")
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "reservation_start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "reservation_end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "reservation_state")
    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }

    public enum ReservationState {
        WORKING_IN_PROGRESS,
        ACCEPTED,
        CLOSED
    }
}
