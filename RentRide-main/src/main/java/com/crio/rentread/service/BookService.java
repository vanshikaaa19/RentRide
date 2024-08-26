package com.crio.rentread.service;

import com.crio.rentread.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book getBookById(Long id);
    List<Book> getBooks(Optional<Boolean> available);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByAuthor(String author);
    Book saveBook(Book book);
    Book updateBook(Long id, Book book);
    boolean deleteBook(Long id);
}
