package algorithms;

import models.Book;

public class SearchAlgorithms {

    public static Book[] linearSearchByTitle(Book[] books, String keyword) {
        if (books == null || books.length == 0) return new Book[0];

        Book[] temp = new Book[books.length];
        int count = 0;
        String key = keyword == null ? "" : keyword.trim().toLowerCase();

        for (Book book : books) {
            if (book != null && book.getTitle().toLowerCase().contains(key)) {
                temp[count++] = book;
            }
        }

        Book[] result = new Book[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    public static Book[] linearSearchByAuthor(Book[] books, String keyword) {
        if (books == null || books.length == 0) return new Book[0];

        Book[] temp = new Book[books.length];
        int count = 0;
        String key = keyword == null ? "" : keyword.trim().toLowerCase();

        for (Book book : books) {
            if (book != null && book.getAuthor().toLowerCase().contains(key)) {
                temp[count++] = book;
            }
        }

        Book[] result = new Book[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    public static Book[] linearSearchByGenre(Book[] books, String keyword) {
        if (books == null || books.length == 0) return new Book[0];

        Book[] temp = new Book[books.length];
        int count = 0;
        String key = keyword == null ? "" : keyword.trim().toLowerCase();

        for (Book book : books) {
            if (book != null && book.getGenre().toLowerCase().contains(key)) {
                temp[count++] = book;
            }
        }

        Book[] result = new Book[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }
}
