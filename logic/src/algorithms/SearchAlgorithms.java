package algorithms;

import models.Book;

/**
 * algorithms.SearchAlgorithms.java
 * DSA Concepts: Linear Search, Binary Search
 *
 * Provides static search methods operating on models.Book arrays.
 *
 * Linear Search:  O(n) — scans every element sequentially.
 *                         Works on unsorted arrays.
 *                         Used for title/author substring search.
 *
 * Binary Search:  O(log n) — divides the search space in half each step.
 *                             Requires SORTED array by bookId.
 *                             Used for exact bookId lookup on sorted catalogue.
 */
public class SearchAlgorithms {

    // -------------------------------------------------------
    // LINEAR SEARCH by Title (substring match, case-insensitive)
    // O(n) — checks every book's title
    // -------------------------------------------------------
    public static Book[] linearSearchByTitle(Book[] books, String keyword) {
        if (books == null || books.length == 0) return new Book[0];

        // Temporary storage for matches (max size = books.length)
        Book[] temp    = new Book[books.length];
        int    count   = 0;
        String keyLower = keyword.toLowerCase().trim();

        for (int i = 0; i < books.length; i++) {
            if (books[i] != null &&
                books[i].getTitle().toLowerCase().contains(keyLower)) {
                temp[count++] = books[i];
            }
        }

        // Trim result array to actual count
        Book[] result = new Book[count];
        for (int i = 0; i < count; i++) result[i] = temp[i];
        return result;
    }

    // -------------------------------------------------------
    // LINEAR SEARCH by Author (substring match, case-insensitive)
    // O(n) — checks every book's author field
    // -------------------------------------------------------
    public static Book[] linearSearchByAuthor(Book[] books, String keyword) {
        if (books == null || books.length == 0) return new Book[0];

        Book[] temp    = new Book[books.length];
        int    count   = 0;
        String keyLower = keyword.toLowerCase().trim();

        for (int i = 0; i < books.length; i++) {
            if (books[i] != null &&
                books[i].getAuthor().toLowerCase().contains(keyLower)) {
                temp[count++] = books[i];
            }
        }

        Book[] result = new Book[count];
        for (int i = 0; i < count; i++) result[i] = temp[i];
        return result;
    }

    // -------------------------------------------------------
    // LINEAR SEARCH by Genre (substring match, case-insensitive)
    // O(n) — checks every book's genre field
    // -------------------------------------------------------
    public static Book[] linearSearchByGenre(Book[] books, String keyword) {
        if (books == null || books.length == 0) return new Book[0];

        Book[] temp    = new Book[books.length];
        int    count   = 0;
        String keyLower = keyword.toLowerCase().trim();

        for (int i = 0; i < books.length; i++) {
            if (books[i] != null &&
                books[i].getGenre().toLowerCase().contains(keyLower)) {
                temp[count++] = books[i];
            }
        }

        Book[] result = new Book[count];
        for (int i = 0; i < count; i++) result[i] = temp[i];
        return result;
    }

    // -------------------------------------------------------
    // BINARY SEARCH by bookId on a SORTED array
    // O(log n) — array must be sorted by bookId (ascending) first
    // Divides search space in half at each step
    // -------------------------------------------------------
    public static Book binarySearchById(Book[] sortedBooks, int targetId) {
        if (sortedBooks == null || sortedBooks.length == 0) return null;

        int low  = 0;
        int high = sortedBooks.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;  // Prevents integer overflow

            if (sortedBooks[mid] == null) {
                high = mid - 1;
                continue;
            }

            int midId = sortedBooks[mid].getBookId();

            if (midId == targetId) {
                return sortedBooks[mid];         // Found!
            } else if (midId < targetId) {
                low = mid + 1;                   // Target is in the right half
            } else {
                high = mid - 1;                  // Target is in the left half
            }
        }
        return null;  // Not found
    }

    // -------------------------------------------------------
    // Display search results to console
    // -------------------------------------------------------
    public static void printResults(Book[] results, String searchTerm) {
        // Intentionally silent. UI output is handled in service/UI layer.
    }
}
