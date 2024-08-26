package com.crio.rentread.service.implementation;

import com.crio.rentread.entity.Book;
import com.crio.rentread.repository.BookRepository;
import com.crio.rentread.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @Override
    public List<Book> getBooks(Optional<Boolean> availableBook) {
        if (availableBook.isPresent() && availableBook.get())
            return bookRepository.findAllByStatusIsTrue();
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() ||
                book.getAuthor() == null || book.getAuthor().isEmpty() ||
                book.getGenre() == null || book.getGenre().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Optional<Book> pBook = bookRepository.findById(id);
        if (pBook.isPresent()) {
            Book uBook = pBook.get();
            if (book.getTitle() != null && !book.getTitle().isEmpty()) {
                uBook.setTitle(book.getTitle());
            }

            if (book.getAuthor() != null && !book.getAuthor().isEmpty()) {
                uBook.setAuthor(book.getAuthor());
            }

            if (book.getGenre() != null && !book.getGenre().isEmpty()) {
                uBook.setGenre(book.getGenre());
            }

            uBook.setStatus(book.isStatus());
            return bookRepository.save(uBook);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }
}
