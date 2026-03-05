package models;

/**
 * models.Book.java
 * Represents a single models.Book entity in the Library System.
 * Used as the data object across all DSA structures.
 */
public class Book {
    private int bookId;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(int bookId, String title, String author, String genre) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }

    // Getters
    public int getBookId()       { return bookId; }
    public String getTitle()     { return title; }
    public String getAuthor()    { return author; }
    public String getGenre()     { return genre; }
    public boolean isAvailable() { return isAvailable; }

    // Setters
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setTitle(String title)          { this.title = title; }
    public void setAuthor(String author)        { this.author = author; }
    public void setGenre(String genre)          { this.genre = genre; }

    @Override
    public String toString() {
        return String.format(
            "[ID: %d | Title: %-30s | Author: %-20s | Genre: %-15s | Status: %s]",
            bookId, title, author, genre, isAvailable ? "Available" : "Issued"
        );
    }
}
