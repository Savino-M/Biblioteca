package com.biblioteca.service;

import com.biblioteca.model.*;
import com.biblioteca.repository.IBookRepository;
import com.biblioteca.repository.IReservationRepository;
import com.biblioteca.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ILoanService loanService;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public User getUserByReservation(Long id) {

        Optional<Reservation> optReservation = reservationRepository.findById(id);
        User user = null;

        if (optReservation.isPresent()) {
            Reservation reservation = optReservation.get();
            user = reservation.getUser();
        }
        return user;
    }

    @Override
    public Book getBookByReservation(Long id) {

        Optional<Reservation> optReservation = reservationRepository.findById(id);
        Book book = null;

        if (optReservation.isPresent()) {
            Reservation reservation = optReservation.get();
            BookCopy bookCopy = reservation.getBookCopy();
            book = bookCopy.getBook();
        }
        return book;
    }

    @Override
    public Set<Reservation> getReservationsByUser(int id) {

        Optional<User> optUser = userRepository.findById(id);
        Set<Reservation> reservationList = null;

        if (optUser.isPresent()) {
            User user = optUser.get();
            reservationList = user.getReservations();
        }
        return reservationList;
    }

    @Override
    public boolean insertReservation(int user_id, String book_isbn) {

        boolean status = false;
        boolean available = false;  //diventa true se la book copy non ha nessun prestito in assoluto
        boolean found = true;  //diventa falso se la book copy ha un prestito aperto
        BookCopy newBookCopy = null;
        User newUser = null;
        int numOpenLoans = 0; //conta il numero di prestiti aperti dell'utente

        Optional<User> optUser = userRepository.findById(user_id);
        Optional<Book> optBook = bookRepository.findById(book_isbn);

        if (optUser.isPresent() && optBook.isPresent()) { //se l'utente e il book esistono

            Book book = optBook.get();
            User user = optUser.get();

            if (user.getReservations().size() == 0) { // se l'utente non ha altre prenotazioni

                Set<Loan> userLoans = user.getLoans();

                for (Loan loan : userLoans) {
                    if (loan.getLoanState() == Loan.LoanState.OPEN) {
                        numOpenLoans++;
                    }
                }

                if (numOpenLoans < 5) { // se l'utente ha meno di 5 prestiti aperti

                    Set<BookCopy> setBooks = book.getBookCopies();

                    for (BookCopy bookCopy : setBooks) { // controllo se c'è una copia del libro disponibile

                        found = true;
                        newBookCopy = bookCopy;
                        newUser = user;
                        Set<Loan> setLoans = bookCopy.getLoans();

                        if (setLoans.size() == 0) { // se il libro non ha nessun prestito
                            available = true;
                            break;

                        } else {
                            for (Loan loan : setLoans) { // controllo se la copia libro non è in nessun prestito aperto

                                if (loan.getLoanState() == Loan.LoanState.OPEN) {
                                    found = false;
                                    break;
                                }
                            }
                            if (found == true) {
                                break;
                            }
                        }
                    }
                    if (available == true || found == true) {
                        Reservation reservation = new Reservation();
                        reservation.setBookCopy(newBookCopy);
                        reservation.setUser(newUser);
                        reservation.setState(Reservation.ReservationState.WORKING_IN_PROGRESS);
                        reservationRepository.save(reservation);

                        status = true;
                    }
                }
            }
        }
        return status;
    }

    @Override
    public boolean acceptReservation(Long id) {
        boolean status = false;

        Optional<Reservation> optReservation = reservationRepository.findById(id);

        if (optReservation.isPresent()) {
            Reservation reservation = optReservation.get();

            if (reservation.getState() == Reservation.ReservationState.WORKING_IN_PROGRESS) {
                reservation.setState(Reservation.ReservationState.ACCEPTED);

                int user_id = reservation.getUser().getId();
                Long bookCopy_id = reservation.getBookCopy().getId();

                status = loanService.insertLoan(user_id, bookCopy_id);
            }
        }

        return status;
    }

    @Override
    public boolean cancelReservation(Long id) {

        boolean status = false;

        Optional<Reservation> optReservation = reservationRepository.findById(id);

        if (optReservation.isPresent()) {
            Reservation reservation = optReservation.get();

            if (reservation.getState() == Reservation.ReservationState.WORKING_IN_PROGRESS) { // se la prenotazione è in lavoro
                reservationRepository.deleteById(id);
                status = true;
            }
        }
        return status;
    }

}
