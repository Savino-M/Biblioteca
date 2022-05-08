package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.model.BookCopy;
import com.biblioteca.model.Loan;
import com.biblioteca.model.User;
import com.biblioteca.repository.IBookCopyRepository;
import com.biblioteca.repository.ILoanRepository;
import com.biblioteca.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LoanService implements ILoanService {

    @Autowired
    private ILoanRepository loanRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookCopyRepository bookCopyRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getLoansByUser(int id) {

        List<Loan> loansList = null;

        try {
            Optional<User> optUser = userRepository.findById(id);

            if (optUser.isPresent()) { // se l'utente esiste
                User user = optUser.get();
                loansList = loanRepository.findByUser(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loansList;
    }

    @Override
    public Book getBook(Long id) {

        Optional<Loan> optLoan = loanRepository.findById(id);
        Book book=null;

        if (optLoan.isPresent()) {
            Loan loan = optLoan.get();
            BookCopy bookCopy = loan.getBookCopy();
            book=bookCopy.getBook();
        }
        return book;
    }

    @Override
    public List<Loan> getLoansByBookCopy(Long id) {

        List<Loan> loansList = null;

        try {
            Optional<BookCopy> optBookCopy = bookCopyRepository.findById(id);

            if (optBookCopy.isPresent()) { // se la copia esiste
                BookCopy bookCopy = optBookCopy.get();
                loansList = loanRepository.findByBookCopy(bookCopy);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loansList;
    }

    @Override
    public boolean insertLoan(int user_id, Long bookCopy_id) {

        boolean status = false;
        boolean found = false;

        Optional<User> optUser = userRepository.findById(user_id);
        Optional<BookCopy> optBookCopy = bookCopyRepository.findById(bookCopy_id);

        if (optUser.isPresent() && optBookCopy.isPresent()) { //se l'utente e la book copy esistono

            BookCopy bookCopy = optBookCopy.get();

            Set<Loan> setLoans = bookCopy.getLoans();

            for (Loan setloan : setLoans) {

                if ((bookCopy_id == setloan.getBookCopy().getId()) && (setloan.getLoanState() == Loan.LoanState.OPEN)) { //se il libro che voglio prestare, ce l'ha già qualcuno
                    found = true;
                }
            }

            if (!found) {  // se il libro non è già in prestito presso qualcuno crei il prestito

                Loan loan = new Loan();
                User user = optUser.get();

                loan.setBookCopy(bookCopy);
                loan.setUser(user);

                loan.setLoanState(Loan.LoanState.OPEN);
                loanRepository.save(loan);

                status = true;
            }
        }
        return status;
    }

    @Override
    public boolean closeLoan(Long id) {

        boolean status = false;

        Optional<Loan> optLoan = loanRepository.findById(id);

        if (optLoan.isPresent()) {
            Loan loan = optLoan.get();
            loan.setLoanState(Loan.LoanState.CLOSED);
            loanRepository.save(loan);
            status = true;
        }

        return status;
    }

}
