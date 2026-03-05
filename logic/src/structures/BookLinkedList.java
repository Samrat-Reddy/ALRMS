package structures;

import models.Book;

/**
 * structures.BookLinkedList.java
 * DSA Concept: Singly Linked List
 *
 * The library catalogue is stored as a Singly Linked List.
 * Each node holds a models.Book and a reference to the next node.
 * Operations: add to end, remove by ID, display all, convert to array.
 *
 * Structure:
 *   HEAD -> [models.Book|next] -> [models.Book|next] -> [models.Book|next] -> null
 */
public class BookLinkedList {

    // -------------------------------------------------------
    // Inner Node class for Singly Linked List
    // -------------------------------------------------------
    private static class Node {
        Book book;
        Node next;

        Node(Book book) {
            this.book = book;
            this.next = null;
        }
    }

    private Node head;   // Head pointer of the singly linked list
    private int  size;   // Number of books in the catalogue

    public BookLinkedList() {
        head = null;
        size = 0;
    }

    // -------------------------------------------------------
    // Add a book at the end of the linked list  O(n)
    // -------------------------------------------------------
    public void addBook(Book book) {
        Node newNode = new Node(book);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // -------------------------------------------------------
    // Remove a book by bookId  O(n)
    // -------------------------------------------------------
    public boolean removeBook(int bookId) {
        if (head == null) return false;
        // If head node is the target
        if (head.book.getBookId() == bookId) {
            head = head.next;
            size--;
            return true;
        }
        // Traverse to find the node before target
        Node current = head;
        while (current.next != null) {
            if (current.next.book.getBookId() == bookId) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // -------------------------------------------------------
    // Get a book by bookId  O(n)
    // -------------------------------------------------------
    public Book getBookById(int bookId) {
        Node current = head;
        while (current != null) {
            if (current.book.getBookId() == bookId) {
                return current.book;
            }
            current = current.next;
        }
        return null;
    }

    // -------------------------------------------------------
    // Display all books in the catalogue  O(n)
    // -------------------------------------------------------
    public void displayAll() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    // -------------------------------------------------------
    // Convert linked list to array (for sorting algorithms)  O(n)
    // -------------------------------------------------------
    public Book[] toArray() {
        Book[] arr = new Book[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.book;
            current = current.next;
        }
        return arr;
    }

    // -------------------------------------------------------
    // Rebuild the linked list from a sorted array  O(n)
    // Used after sorting to update the catalogue order
    // -------------------------------------------------------
    public void fromArray(Book[] arr) {
        head = null;
        size = 0;
        for (Book book : arr) {
            Node newNode = new Node(book);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }
    }

    // -------------------------------------------------------
    // Check if a bookId already exists  O(n)
    // -------------------------------------------------------
    public boolean containsBookId(int bookId) {
        Node current = head;
        while (current != null) {
            if (current.book.getBookId() == bookId) return true;
            current = current.next;
        }
        return false;
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return head == null; }
}
