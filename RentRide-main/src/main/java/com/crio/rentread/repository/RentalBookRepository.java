package com.crio.rentread.repository;

import com.crio.rentread.entity.Book;
import com.crio.rentread.entity.RentalBook;
import com.crio.rentread.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalBookRepository extends JpaRepository<RentalBook,Long> {
    int countRentalBookByUserId(Long user_id);
    Optional<RentalBook> findByUserAndBook(User user, Book book);
}
