package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping(value = "/welcome")
    public String welcome() {
        return "Welcome";
    }

    /**
     * Questo metodo restituisce tutti i libri
     *
     * @return lista di libri
     */
    @GetMapping(value = "/book/getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Questo metodo restituisce un libro in base alla isbn passata
     *
     * @param isbn l'identificativo del libro da trovare
     * @return un libro
     */
    @GetMapping(value = "/book/getBookById")
    public Book getBookById(@RequestParam String isbn) {
        return bookService.getBookById(isbn);
    }

    /**
     * Questo metodo restituisce i libri in base al titolo
     *
     * @param title dei libri da trovare
     * @return una lista di libri
     */
    @GetMapping(value = "/book/getBooksByTitle")
    public List<Book> getBooksByTitle(@RequestParam String title) {
        return bookService.getBooksByTitle(title);
    }

    /**
     * Questo metodo restituisce i libri in base all'autore passato
     *
     * @param author: l'autore dei libri da trovare
     * @return una lista di libri
     */
    @GetMapping(value = "/book/getBooksByAuthor")
    public List<Book> getBooksByAuthor(@RequestParam String author) {
        return bookService.getBooksByAuthor(author);
    }

    /**
     * Questo metodo inserisce un nuovo libro
     *
     * @param book il libro da inserire
     * @return esito
     */
    @PostMapping(value = "/book/saveBook")
    public boolean insertBook(@RequestBody Book book) {
        return bookService.insertBook(book);
    }

    /**
     * Questo metodo aggiorna il titolo di un libro
     *
     * @param isbn  del libro da aggiornare
     * @param title il nuovo titolo
     * @return esito
     */
    @PostMapping(value = "/book/updateBookTitle")
    public boolean updateBookTitle(@RequestParam String isbn, @RequestParam String title) {
        return bookService.updateBookTitle(isbn, title);
    }

    /**
     * Questo metodo elimina un libro(mettendolo fuori catalogo)
     *
     * @param isbn del libro da rimuovere
     * @return esito
     */
    @PostMapping(value = "/book/deleteBook")
    public boolean deleteBook(@RequestParam String isbn) {
        return bookService.deleteBook(isbn);
    }

}
