package com.lowlevel.design.lld.libraryManagement;

/**
 * The library management system should allow librarians to manage books, members, and borrowing activities.
 * The system should support adding, updating, and removing books from the library catalog.
 * Each book should have details such as title, author, ISBN, publication year, and availability status.
 * The system should allow members to borrow and return books.
 * Each member should have details such as name, member ID, contact information, and borrowing history.
 * The system should enforce borrowing rules, such as a maximum number of books that can be borrowed at a time and borrow duration.
 * The system should handle concurrent access to the library catalog and member records.
 * The system should be extensible to accommodate future enhancements and new features.
 *
 * Book:
 *      title, author, ISBN, publication year
 *
 * Booking:
 *      user, borrowDate, bookId, status, returnDate
 *
 * User:
 *      type: admin, reader
 *
 * Fine:
 *      bookingId
 *      amount
 *      userId
 *      date
 *
 * BookingService:
 *      1. borrow book
 *      2. return book (bookingId)
 *      3. Booking status
 *
 * Catalogue:
 *      1. bookId
 *      2. count
 *      3. version
 *
 *
 * CatalogueService:
 *      1. add book
 *      2. remove book
 *      3. update book
 *
 * UserService:
 *      1. addUser
 *      2. updateUser
 *      3. deleteUser
 *
 *
 * FineService (Strategy Pattern):
 *      1. calculateFine();
 */





public class LibraryManagement {
}
