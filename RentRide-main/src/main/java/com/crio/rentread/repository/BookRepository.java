package com.crio.rentread.repository;

import com.crio.rentread.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByGenre(String genre);
    List<Book> findAllByAuthor(String author);
    List<Book> findAllByStatusIsTrue();

}
