package com.crio.rentread.service;

import com.crio.rentread.entity.RentalBook;
import com.crio.rentread.entity.User;

public interface RentalService {
    RentalBook takeBook(Long rentalBook, User user);
    boolean returnBook(Long rentalBook, User user);
}
