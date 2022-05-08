package com.biblioteca.repository;

import com.biblioteca.model.Book;
import com.biblioteca.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookCopyRepository extends JpaRepository<BookCopy, Long> {

    public List<BookCopy> findByBook(Book book);
}
