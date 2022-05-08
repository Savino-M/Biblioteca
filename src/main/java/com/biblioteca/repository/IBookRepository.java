package com.biblioteca.repository;

import com.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, String> {

    public Optional<List<Book>> findByTitle(String title);

    public Optional<List<Book>> findByAuthor(String author);

}
