package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.model.Reservation;
import com.biblioteca.model.User;

import java.util.List;
import java.util.Set;

public interface IReservationService {

    public List<Reservation> getAllReservations();

    public User getUserByReservation(Long id);

    public Book getBookByReservation(Long id);

    public Set<Reservation> getReservationsByUser(int id);

    public boolean insertReservation(int user_id, String book_isbn);

    public boolean acceptReservation(Long id);

    public boolean cancelReservation(Long id);

}
