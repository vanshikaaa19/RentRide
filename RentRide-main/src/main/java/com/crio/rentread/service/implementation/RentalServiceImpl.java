package com.crio.rentread.service.implementation;

import com.crio.rentread.entity.Book;
import com.crio.rentread.entity.RentalBook;
import com.crio.rentread.entity.User;
import com.crio.rentread.repository.BookRepository;
import com.crio.rentread.repository.RentalBookRepository;
import com.crio.rentread.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalBookRepository rentalBookRepository;
    private final BookRepository bookRepository;

    @Override
    public RentalBook takeBook(Long bookId, User user) {
        RentalBook rentalBook = new RentalBook();

        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        if (!book.get().isStatus())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Book is not available to rent");
        rentalBook.setBook(book.get());

        if (rentalBookRepository.countRentalBookByUserId(user.getId()) > 2)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User has already 2 books");
        rentalBook.setUser(user);

        return rentalBookRepository.save(rentalBook);
    }


    @Override
    public boolean returnBook(Long bookId, User user) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        Optional<RentalBook> rentalBook = rentalBookRepository.findByUserAndBook(user, book.get());
        if (rentalBook.isPresent())
        {
            rentalBookRepository.deleteById(rentalBook.get().getId());
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user has not taken this book");
    }

}
