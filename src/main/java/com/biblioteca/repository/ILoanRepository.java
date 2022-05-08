package com.biblioteca.repository;

import com.biblioteca.model.BookCopy;
import com.biblioteca.model.Loan;
import com.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILoanRepository extends JpaRepository<Loan, Long> {

    public List<Loan> findByUser(User user);

    public List<Loan> findByBookCopy(BookCopy bookCopy);
}
