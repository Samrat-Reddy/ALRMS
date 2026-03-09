package structures;

import models.Book;

public class BookLinkedList {
    private static class Node {
        Book book;
        Node next;

        Node(Book book) {
            this.book = book;
        }
    }

    private Node head;
    private int size;

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

    public void fromArray(Book[] arr) {
        head = null;
        size = 0;
        for (Book book : arr) {
            addBook(book);
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
