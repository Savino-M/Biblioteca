package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.model.BookCopy;
import com.biblioteca.model.Loan;

import java.util.List;

public interface ILoanService {

    public List<Loan> getAllLoans();

    public List<Loan> getLoansByUser(int id);

    public Book getBook(Long id);

    public List<Loan> getLoansByBookCopy(Long id);

    public boolean insertLoan(int user_id, Long bookCopy_id);

    public boolean closeLoan(Long id);
}
