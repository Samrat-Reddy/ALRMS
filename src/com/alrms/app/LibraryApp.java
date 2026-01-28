package com.alrms.app;

import com.alrms.model.Book;
import com.alrms.service.LibraryManager;

public class LibraryApp {

    public static void main(String[] args) {

        LibraryManager manager = new LibraryManager();

        Book b1 = new Book("B101", "Java Programming", "Herbert Schildt", "Technology", 3);
        Book b2 = new Book("B102", "Data Structures", "Mark Allen", "Technology", 2);

        System.out.println(manager.addBook(b1)); // true
        System.out.println(manager.addBook(b2)); // true
        System.out.println(manager.addBook(b1)); // false (duplicate)

        Book found = manager.searchBook("B101");

        if (found != null) {
            System.out.println("Book found: " + found.getTitle());
            System.out.println("Available copies: " + found.getAvailableCopies());
        } else {
            System.out.println("Book not found");
        }
    }
}
