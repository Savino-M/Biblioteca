package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IBookCopyService bookCopyService;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(String isbn) {

        Book book = null;
        Optional<Book> optBook = bookRepository.findById(isbn);

        if (optBook.isPresent()) {
            book = optBook.get();
        }

        return book;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {

        List<Book> books = null;
        Optional<List<Book>> optBook = bookRepository.findByTitle(title);

        if (optBook.isPresent()) {
            books = optBook.get();
        }

        return books;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {

        List<Book> books = new ArrayList<Book>();
        Optional<List<Book>> optBooks = bookRepository.findByAuthor(author);

        if (optBooks.isPresent()) {
            books = optBooks.get();
        }

        return books;
    }

    @Override
    public boolean insertBook(Book book) {

        boolean operationStatus = false;

        try {
            Optional<Book> optBook = bookRepository.findById(book.getIsbn());

            if (optBook.isPresent()) { // se il libro è già presente

            } else {
                book.setBookState(Book.BookState.FUORI_CATALOGO);
                book.setCopiesNumber(0);
                bookRepository.save(book);
                operationStatus = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operationStatus;
    }

    @Override
    public boolean updateBookTitle(String isbn, String title) {

        boolean operationStatus = false;

        Optional<Book> optBook = bookRepository.findById(isbn);

        if (optBook.isPresent()) {
            Book book = optBook.get();
            book.setTitle(title);
            bookRepository.save(book);
            operationStatus = true;
        }

        return operationStatus;
    }

    @Override
    public boolean deleteBook(String isbn) {

        boolean operationStatus = false;
        Optional<Book> optBook = bookRepository.findById(isbn);

        if (optBook.isPresent()) {
            Book book = optBook.get();
            book.setBookState(Book.BookState.FUORI_CATALOGO);
            bookRepository.save(book);
            operationStatus = true;
        }

        return operationStatus;
    }

}
