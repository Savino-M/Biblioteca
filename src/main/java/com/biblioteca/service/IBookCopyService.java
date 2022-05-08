package com.biblioteca.service;

import com.biblioteca.model.BookCopy;

import java.util.List;

public interface IBookCopyService {

    public List<BookCopy> getAllBookCopies();

    public BookCopy getBookCopyById(Long id);

    public List<BookCopy> getBookCopyByIsbn(String isbn);

    public boolean insertBookCopies(String isbn, int copies);

    public boolean deleteBookCopy(Long id);
}
