package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.model.BookCopy;
import com.biblioteca.repository.IBookCopyRepository;
import com.biblioteca.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookCopyService implements IBookCopyService {

    @Autowired
    private IBookCopyRepository bookCopyRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    @Override
    public BookCopy getBookCopyById(Long id) {

        BookCopy bookCopy = null;
        Optional<BookCopy> optBookCopy = bookCopyRepository.findById(id);

        if (optBookCopy.isPresent()) {
            bookCopy = optBookCopy.get();
        }

        return bookCopy;
    }

    @Override
    public List<BookCopy> getBookCopyByIsbn(String isbn) {

        List<BookCopy> bookCopies = null;

        try {
            Optional<Book> optBook = bookRepository.findById(isbn);

            if (optBook.isPresent()) { // se il libro esiste
                Book book = optBook.get();
                bookCopies = bookCopyRepository.findByBook(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookCopies;
    }

    @Override
    public boolean insertBookCopies(String isbn, int copies) {

        boolean status = false;

        try {
            Optional<Book> optBook = bookRepository.findById(isbn); //verifico se esiste già un libro
            Book book = null;

            if (optBook.isPresent()) {
                book = optBook.get();
                book.setCopiesNumber(book.getCopiesNumber() + copies); //aggiorno il numero di copie del libro
                book.setBookState(Book.BookState.IN_CATALOGO);
            }

            for (int i = 0; i < copies; i++) {
                BookCopy bookCopy = new BookCopy();
                bookCopy.setBook(book);
                bookCopyRepository.save(bookCopy);
            }

            bookRepository.save(book);
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean deleteBookCopy(Long id) {

        boolean status = false;

        try {
            Optional<BookCopy> optBookCopy = bookCopyRepository.findById(id); //trovo il libro a cui corrisponde la copia

            if (optBookCopy.isPresent()) {

                BookCopy bookCopy = optBookCopy.get(); // ricavo la book copy
                Book book = bookCopy.getBook(); // ricavo il libro di riferimento

                book.setCopiesNumber(book.getCopiesNumber() - 1); // aggiorno il numero di copie per il libro

                if (book.getCopiesNumber() == 0) {
                    book.setBookState(Book.BookState.FUORI_CATALOGO);
                }

                bookCopyRepository.deleteById(id);
                bookRepository.save(book);
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
