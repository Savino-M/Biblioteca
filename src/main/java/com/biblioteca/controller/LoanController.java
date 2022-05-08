package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.model.BookCopy;
import com.biblioteca.model.Loan;
import com.biblioteca.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class LoanController {

    @Autowired
    private ILoanService loanService;

    /**
     * Questo metodo restituisce tutti i prestiti
     *
     * @return lista di prestiti
     */
    @GetMapping(value = "/loan/getAllLoans")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    /**
     * Questo metodo restituisce i prestiti di un utente
     *
     * @param id dell'utente
     * @return lista di prestiti
     */
    @GetMapping(value = "/loan/getLoansByUser")
    public List<Loan> getLoansByUser(@RequestParam int id) {
        return loanService.getLoansByUser(id);
    }

    /**
     * Questo metodo restituisce il book associato al prestito
     *
     * @param id del prestito
     * @return book
     */
    @GetMapping(value = "/loan/getBook")
    public Book getBook(@RequestParam Long id) {
        return loanService.getBook(id);
    }

    /**
     * Questo metodo restituisce i prestiti di un libro
     *
     * @param id dela book copy
     * @return lista di prestiti
     */
    @GetMapping(value = "/loan/getLoansByBookCopy")
    public List<Loan> getLoansByBookCopy(@RequestParam Long id) {
        return loanService.getLoansByBookCopy(id);
    }

    /**
     * Questo metodo inserisce un nuovo prestito
     *
     * @param user_id     utente che fa il prestito
     * @param bookCopy_id copia libro da prestare all'utente
     * @return esito
     */
    @PostMapping(value = "/loan/saveLoan")
    public boolean insertLoan(@RequestParam int user_id, Long bookCopy_id) {
        return loanService.insertLoan(user_id, bookCopy_id);
    }

    /**
     * Questo metodo chiude un prestito
     *
     * @param id del prestito da eliminare
     * @return esito
     */
    @PostMapping(value = "/loan/closeLoan")
    public boolean closeLoan(@RequestParam Long id) {
        return loanService.closeLoan(id);
    }
}
