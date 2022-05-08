package com.biblioteca.service;

import com.biblioteca.model.Book;

import java.util.List;

public interface IBookService {

    public List<Book> getAllBooks();

    public Book getBookById(String isbn);

    public List<Book> getBooksByTitle(String title);

    public List<Book> getBooksByAuthor(String author);

    public boolean insertBook(Book book);

    public boolean deleteBook(String isbn);

    public boolean updateBookTitle(String isbn, String title);
}
