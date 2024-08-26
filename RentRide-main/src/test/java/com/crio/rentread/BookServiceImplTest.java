package com.crio.rentread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.crio.rentread.entity.Book;
import com.crio.rentread.repository.BookRepository;
import com.crio.rentread.service.implementation.BookServiceImpl;

@SpringBootTest
public class BookServiceImplTest {

    private BookRepository bookRepository;
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository);
    }

    // Retrieve a book by its ID successfully
    @Test
    public void test_get_book_by_id_success() {
        Book book = new Book(1L, "Title", "Author", "Genre", true, new ArrayList<>());
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Title", result.getTitle());
    }

    // Attempt to retrieve a book with a non-existent ID
    @Test
    public void test_get_book_by_id_not_found() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            bookService.getBookById(1L);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Book not found", exception.getReason());
    }

    // Retrieve all books when no availability filter is applied
    @Test
    public void test_retrieve_all_books_no_filter() {
        List<Book> expectedBooks = new ArrayList<>();
        Mockito.when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooks(Optional.empty());

        Assertions.assertEquals(expectedBooks, actualBooks);
    }

    // Retrieve only available books when availability filter is true
    @Test
    public void test_retrieve_available_books() {
        List<Book> expectedAvailableBooks = new ArrayList<>();
        Mockito.when(bookRepository.findAllByStatusIsTrue()).thenReturn(expectedAvailableBooks);
        Optional<Boolean> availableFilter = Optional.of(true);

        List<Book> actualAvailableBooks = bookService.getBooks(availableFilter);

        Assertions.assertEquals(expectedAvailableBooks, actualAvailableBooks);
    }

    // Retrieve books by genre successfully
    @Test
    public void test_retrieve_books_by_genre_successfully() {
        String genre = "Fiction";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L, "Book1", "Author1", genre, true, new ArrayList<>()));
        expectedBooks.add(new Book(2L, "Book2", "Author2", genre, true, new ArrayList<>()));
        Mockito.when(bookRepository.findAllByGenre(genre)).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooksByGenre(genre);

        Assertions.assertEquals(expectedBooks, actualBooks);
    }

    // Retrieve books by author successfully
    @Test
    public void test_retrieve_books_by_author_successfully() {
        String author = "Author1";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L, "Book1", author, "Fiction", true, new ArrayList<>()));
        expectedBooks.add(new Book(2L, "Book2", author, "Non-Fiction", true, new ArrayList<>()));
        Mockito.when(bookRepository.findAllByAuthor(author)).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooksByAuthor(author);

        Assertions.assertEquals(expectedBooks, actualBooks);
    }

    // Save a new book successfully
    @Test
    public void test_save_new_book_successfully() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setGenre("Test Genre");
        book.setStatus(true);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        Assertions.assertNotNull(savedBook);
        Assertions.assertEquals("Test Book", savedBook.getTitle());
        Assertions.assertEquals("Test Author", savedBook.getAuthor());
        Assertions.assertEquals("Test Genre", savedBook.getGenre());
        Assertions.assertTrue(savedBook.isStatus());
    }

    // Update an existing book's details successfully
    @Test
    public void test_update_existing_book_details_successfully() {
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Existing Book");
        existingBook.setAuthor("Existing Author");
        existingBook.setGenre("Existing Genre");
        existingBook.setStatus(true);

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.updateBook(bookId, updatedBook);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Updated Title", result.getTitle());
        Assertions.assertEquals("Existing Author", result.getAuthor());
        Assertions.assertEquals("Existing Genre", result.getGenre());
        Assertions.assertTrue(result.isStatus());
    }

    // Delete a book by its ID successfully
    @Test
    public void test_delete_book_success() {
        Long id = 1L;
        Book book = new Book(id, "Title", "Author", "Genre", true, new ArrayList<>());

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).deleteById(id);

        boolean result = bookService.deleteBook(id);

        Assertions.assertTrue(result);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(id);
    }

    // Attempt to delete a book with a non-existent ID
    @Test
    public void test_delete_book_non_existent_id() {
        Long id = 1L;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> bookService.deleteBook(id));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(id);
    }

    // Attempt to update a book with a non-existent ID
    @Test
    public void test_attempt_to_update_book_with_non_existent_id() {
        Long nonExistentId = 100L;
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setGenre("Updated Genre");
        updatedBook.setStatus(true);

        Mockito.when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> bookService.updateBook(nonExistentId, updatedBook));
    }

    // Attempt to save a book with missing required fields
    @Test
    public void test_attempt_to_save_book_with_missing_required_fields() {
        Book book = new Book();

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            bookService.saveBook(book);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    // Ensure the status of a book is updated correctly during update
    @Test
    public void test_ensure_status_of_book_updated_correctly_during_update() {
        Long id = 1L;
        Book existingBook = new Book(1L, "Existing Title", "Existing Author", "Existing Genre", true, new ArrayList<>());
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", "Updated Genre", false, new ArrayList<>());
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(id, updatedBook);

        Assertions.assertFalse(result.isStatus());
    }

    // Validate the response when no books are available in the repository
    @Test
    public void test_validate_no_books_available() {
        Mockito.when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        List<Book> books = bookService.getBooks(Optional.empty());

        Assertions.assertTrue(books.isEmpty());
    }

    // Verify the behavior when the repository is empty
    @Test
    public void test_verify_empty_repository_behavior() {
        Mockito.when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        List<Book> books = bookService.getBooks(Optional.of(true));

        Assertions.assertTrue(books.isEmpty());
    }

    // Verify that the correct book is returned when a valid ID is provided
    @Test
    public void test_verify_correct_book_returned_for_valid_id() {
        Long validId = 1L;
        Book validBook = new Book();
        validBook.setId(validId);
        validBook.setTitle("Valid Book");
        validBook.setAuthor("Valid Author");
        validBook.setGenre("Valid Genre");
        validBook.setStatus(true);

        Mockito.when(bookRepository.findById(validId)).thenReturn(Optional.of(validBook));

        Book book = bookService.getBookById(validId);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(validId, book.getId());
        Assertions.assertEquals("Valid Book", book.getTitle());
    }
}
