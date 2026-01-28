package com.alrms.service;

import com.alrms.model.Book;
import java.util.ArrayList;

public class LibraryManager {

    private ArrayList<Book> books = new ArrayList<>();

    // Add new book
    public boolean addBook(Book book) {

        // prevent duplicate book ID
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(book.getBookId())) {
                return false;
            }
        }

        books.add(book);
        return true;
    }

    // Search book by ID
    public Book searchBook(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(bookId)) {
                return b;
            }
        }
        return null;
    }

    // Check availability
    public boolean isBookAvailable(String bookId) {
        Book book = searchBook(bookId);
        return book != null && book.getAvailableCopies() > 0;
    }
}
