package structures;

import models.Book;

/**
 * structures.HashTable.java
 * DSA Concept: Hash Table with Separate Chaining
 *
 * Used for O(1) average-case book lookup by bookId.
 * Collision resolution: Separate Chaining (each bucket holds a linked list).
 * Rehashing: When load factor exceeds 0.75, the table doubles in size
 *            and all entries are rehashed into the new table.
 *
 * Hash Function: hashCode = bookId % tableSize  (Division Method)
 *
 * Visual (tableSize = 7):
 *   Index 0: -> [models.Book(7)] -> null
 *   Index 1: -> [models.Book(1)] -> [models.Book(8)] -> null   (collision chained)
 *   Index 2: -> null
 *   Index 3: -> [models.Book(3)] -> null
 *   ...
 */
public class HashTable {

    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private static final int    INITIAL_CAPACITY      = 11;  // prime number reduces collisions

    // -------------------------------------------------------
    // Inner class for a chained node
    // -------------------------------------------------------
    private static class HashNode {
        int      key;    // bookId
        Book     value;
        HashNode next;   // next node in the same bucket (chaining)

        HashNode(int key, Book value) {
            this.key   = key;
            this.value = value;
            this.next  = null;
        }
    }

    private HashNode[] buckets;    // Array of bucket heads
    private int        capacity;   // Current table size
    private int        size;       // Number of entries stored

    public HashTable() {
        this.capacity = INITIAL_CAPACITY;
        this.buckets  = new HashNode[capacity];
        this.size     = 0;
    }

    // -------------------------------------------------------
    // Hash Function: Division Method  O(1)
    // Maps bookId to a bucket index
    // -------------------------------------------------------
    private int hash(int bookId) {
        return Math.abs(bookId) % capacity;
    }

    // -------------------------------------------------------
    // Insert a book into the hash table  O(1) average
    // If key already exists, update the value
    // -------------------------------------------------------
    public void insert(Book book) {
        // Check load factor and rehash if needed
        if ((double) size / capacity >= LOAD_FACTOR_THRESHOLD) {
            rehash();
        }

        int index = hash(book.getBookId());
        HashNode current = buckets[index];

        // Traverse chain to check for existing key (update)
        while (current != null) {
            if (current.key == book.getBookId()) {
                current.value = book;  // Update existing
                return;
            }
            current = current.next;
        }

        // Insert new node at front of chain (prepend)
        HashNode newNode = new HashNode(book.getBookId(), book);
        newNode.next    = buckets[index];
        buckets[index]  = newNode;
        size++;
    }

    // -------------------------------------------------------
    // Search for a book by bookId  O(1) average
    // -------------------------------------------------------
    public Book search(int bookId) {
        int index = hash(bookId);
        HashNode current = buckets[index];

        while (current != null) {
            if (current.key == bookId) {
                return current.value;
            }
            current = current.next;
        }
        return null;  // Not found
    }

    // -------------------------------------------------------
    // Delete a book entry by bookId  O(1) average
    // -------------------------------------------------------
    public boolean delete(int bookId) {
        int index = hash(bookId);
        HashNode current  = buckets[index];
        HashNode previous = null;

        while (current != null) {
            if (current.key == bookId) {
                if (previous == null) {
                    buckets[index] = current.next;  // Remove head of chain
                } else {
                    previous.next = current.next;   // Bypass node
                }
                size--;
                return true;
            }
            previous = current;
            current  = current.next;
        }
        return false;  // Not found
    }

    // -------------------------------------------------------
    // Rehashing: Double the table size and re-insert all entries
    // DSA Concept: Rehashing — triggered when load factor >= 0.75
    // -------------------------------------------------------
    private void rehash() {
        int          oldCapacity = capacity;
        HashNode[]   oldBuckets  = buckets;

        capacity = nextPrime(capacity * 2);
        buckets  = new HashNode[capacity];
        size     = 0;

        for (int i = 0; i < oldCapacity; i++) {
            HashNode current = oldBuckets[i];
            while (current != null) {
                insert(current.value);  // Re-insert with new hash
                current = current.next;
            }
        }
    }

    // -------------------------------------------------------
    // Helper: Find next prime >= n (for better hash distribution)
    // -------------------------------------------------------
    private int nextPrime(int n) {
        while (!isPrime(n)) n++;
        return n;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // -------------------------------------------------------
    // Display hash table internals (for educational/debug view)
    // -------------------------------------------------------
    public void displayTable() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    public int getSize()     { return size; }
    public int getCapacity() { return capacity; }
}
