package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.model.Reservation;
import com.biblioteca.model.User;
import com.biblioteca.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    /**
     * Questo metodo restituisce tutte le prenotazioni
     *
     * @return lista di prenotazioni
     */
    @GetMapping(value = "/reservation/getAllReservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    /**
     * Questo metodo restituisce l'utente di una prenotazione
     *
     * @param id della reservation
     * @return utente che ha fatto la prenotazione
     */
    @GetMapping(value = "/reservation/getUserByReservation")
    public User getUserByReservation(@RequestParam Long id) {
        return reservationService.getUserByReservation(id);
    }

    /**
     * Questo metodo restituisce il book di una prenotazione
     *
     * @param id della reservation
     * @return book della prenotazione
     */
    @GetMapping(value = "/reservation/getBookByReservation")
    public Book getBookByReservation(@RequestParam Long id) {
        return reservationService.getBookByReservation(id);
    }

    /**
     * Questo metodo restituisce le prenotazioni di un utente
     *
     * @param id dell'utente
     * @return lista di prenotazioni
     */
    @GetMapping(value = "/reservation/getReservationsByUser")
    public Set<Reservation> getReservationsByUser(@RequestParam int id) {
        return reservationService.getReservationsByUser(id);
    }

    /**
     * Questo metodo inserisce una nuova prenotazione
     *
     * @param user_id   utente che fa il prestito
     * @param book_isbn libro da prestare all'utente
     * @return esito
     */
    @PostMapping(value = "/reservation/saveReservation")
    public boolean insertReservation(@RequestParam int user_id, String book_isbn) {
        return reservationService.insertReservation(user_id, book_isbn);
    }

    /**
     * Questo metodo accetta una prenotazione
     *
     * @param id della prenotazione
     * @return esito
     */
    @PostMapping(value = "/reservation/acceptReservation")
    public boolean acceptReservation(@RequestParam Long id) {
        return reservationService.acceptReservation(id);
    }

    /**
     * Questo metodo annulla una prenotazione
     *
     * @param id della prenotazione
     * @return esito
     */
    @PostMapping(value = "/reservation/cancelReservation")
    public boolean cancelReservation(@RequestParam Long id) {
        return reservationService.cancelReservation(id);
    }


}
