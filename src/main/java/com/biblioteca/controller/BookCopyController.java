package com.biblioteca.controller;

import com.biblioteca.model.BookCopy;
import com.biblioteca.service.IBookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookCopyController {

    @Autowired
    private IBookCopyService bookCopyService;

    /**
     * Questo metodo restituisce tutte le copie libro
     *
     * @return lista di book copies
     */
    @GetMapping(value = "/bookCopy/getAllBookCopies")
    public List<BookCopy> getAllBooksCopies() {
        return bookCopyService.getAllBookCopies();
    }

    /**
     * Questo metodo restituisce una copia libro in base all'id passato
     *
     * @param id della book copy da trovare
     * @return una book copy
     */
    @GetMapping(value = "/bookCopy/getBookCopyById")
    public BookCopy getBookCopyById(@RequestParam Long id) {
        return bookCopyService.getBookCopyById(id);
    }

    /**
     * Questo metodo restituisce le copie di un libro
     *
     * @param isbn del libro
     * @return le copie del libro
     */
    @GetMapping(value = "/bookCopy/getBookCopiesByIsbn")
    public List<BookCopy> getBookCopyByIsbn(@RequestParam String isbn) {
        return bookCopyService.getBookCopyByIsbn(isbn);
    }

    /**
     * Questo metodo inserisce una o più book copies di un libro
     *
     * @param isbn      del libro di riferimento
     * @param numCopies di copie da inserire
     * @return esito
     */
    @PostMapping(value = "/bookCopy/saveBookCopies")
    public boolean insertBookCopies(@RequestParam String isbn, @RequestParam(value = "numCopies") int numCopies) {
        return bookCopyService.insertBookCopies(isbn, numCopies);
    }

    /**
     * Questo metodo elimina la copia di un libro
     *
     * @param id della book copy da eliminare
     * @return esito
     */
    @PostMapping(value = "/bookCopy/deleteBookCopy")
    public boolean deleteBookCopy(@RequestParam Long id) {
        return bookCopyService.deleteBookCopy(id);
    }

}
