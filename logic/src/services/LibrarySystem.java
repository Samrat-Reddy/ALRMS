package services;

import algorithms.SearchAlgorithms;
import algorithms.SortAlgorithms;
import models.Book;
import models.IssueRecord;
import models.IssuedBookList;
import models.Student;
import structures.BookLinkedList;
import structures.HashTable;
import structures.IssueQueue;

import java.util.Scanner;

public class LibrarySystem {
    private final BookLinkedList catalogue;
    private final IssuedBookList issuedBooks;
    private final IssueQueue issueQueue;
    private final HashTable bookHashTable;
    private final Scanner scanner;

    private int nextBookId = 100;
    private int nextRecordId = 1;
    private int nextRequestId = 1;
    private int nextStudentId = 1001;
    private int currentDayCount = 1;

    private static final double FINE_PER_DAY = 5.0;
    private static final int LOAN_DAYS = 14;

    public LibrarySystem(Scanner scanner) {
        this.catalogue = new BookLinkedList();
        this.issuedBooks = new IssuedBookList();
        this.issueQueue = new IssueQueue();
        this.bookHashTable = new HashTable();
        this.scanner = scanner;
        loadSampleData();
    }

    public void viewCatalogue() {
        Book[] books = catalogue.toArray();
        if (books.length == 0) {
            System.out.println("Catalogue is empty.");
            return;
        }

        System.out.println("\n========== LIBRARY CATALOGUE ==========");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
        System.out.println("Total books: " + books.length);
    }

    public void searchBook() {
        if (catalogue.isEmpty()) {
            System.out.println("Catalogue is empty. No books to search.");
            return;
        }

        System.out.println("\n--- SEARCH BOOK ---");
        System.out.println("1 Search by Title");
        System.out.println("2 Search by Author");
        System.out.println("3 Search by Genre");
        System.out.println("4 Search by Book ID");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().trim();
        Book[] books = catalogue.toArray();

        switch (choice) {
            case "1":
                System.out.print("Enter title keyword: ");
                printSearchResults(SearchAlgorithms.linearSearchByTitle(books, scanner.nextLine().trim()));
                break;
            case "2":
                System.out.print("Enter author keyword: ");
                printSearchResults(SearchAlgorithms.linearSearchByAuthor(books, scanner.nextLine().trim()));
                break;
            case "3":
                System.out.print("Enter genre keyword: ");
                printSearchResults(SearchAlgorithms.linearSearchByGenre(books, scanner.nextLine().trim()));
                break;
            case "4":
                System.out.print("Enter Book ID: ");
                try {
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    Book found = bookHashTable.search(id);
                    if (found == null) {
                        System.out.println("Book with ID " + id + " not found.");
                    } else {
                        System.out.println("Book found: " + found);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Book ID.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void sortCatalogue() {
        if (catalogue.isEmpty()) {
            System.out.println("Catalogue is empty. Nothing to sort.");
            return;
        }

        Book[] books = catalogue.toArray();
        SortAlgorithms.mergeSortByTitle(books);
        catalogue.fromArray(books);

        System.out.println("Catalogue sorted using Merge Sort (by Title).");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
    }

    public void addBook() {
        System.out.println("\n--- ADD BOOK ---");
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            System.out.println("Title, Author, and Genre are required.");
            return;
        }

        Book book = new Book(nextBookId++, title, author, genre);
        catalogue.addBook(book);
        bookHashTable.insert(book);
        System.out.println("Book added successfully. Book ID: " + book.getBookId());
    }

    public void addIssueRequest() {
        if (catalogue.isEmpty()) {
            System.out.println("No books in the catalogue.");
            return;
        }

        System.out.println("\n--- ISSUE REQUEST ---");
        System.out.print("Enter Book ID: ");

        int bookId;
        try {
            bookId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
            return;
        }

        Book book = bookHashTable.search(bookId);
        if (book == null) {
            System.out.println("Book not found in catalogue.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine().trim();
        if (studentName.isEmpty()) {
            System.out.println("Student name is required.");
            return;
        }

        IssueQueue.IssueRequest request = new IssueQueue.IssueRequest(
            nextRequestId++,
            bookId,
            nextStudentId++,
            studentName,
            "STUDENT",
            "Day-" + currentDayCount
        );

        issueQueue.enqueue(request);
        System.out.println("Request queued. Request ID: " + request.getRequestId());
        System.out.println("Pending requests: " + issueQueue.getSize());
    }

    public void processIssueRequest() {
        System.out.println("\n--- PROCESS ISSUE REQUEST ---");
        if (issueQueue.isEmpty()) {
            System.out.println("No pending issue requests.");
            return;
        }

        IssueQueue.IssueRequest request = issueQueue.dequeue();
        Book book = bookHashTable.search(request.getBookId());

        if (book == null) {
            System.out.println("Book no longer exists. Request skipped.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is currently issued. Request skipped.");
            return;
        }

        Student student = new Student(
            request.getStudentId(),
            request.getStudentName(),
            request.getStudentName().toLowerCase().replace(" ", ".") + "@library.edu",
            "General"
        );

        int issueDay = currentDayCount;
        int dueDay = issueDay + LOAN_DAYS;

        IssueRecord record = new IssueRecord(
            nextRecordId++,
            book,
            student,
            request.getIssuedBy(),
            "Day-" + issueDay,
            "Day-" + dueDay,
            issueDay,
            dueDay
        );

        issuedBooks.addRecord(record);
        book.setAvailable(false);
        bookHashTable.insert(book);

        System.out.println("Book issued successfully.");
        System.out.println("Record ID: " + record.getRecordId());
        System.out.println("Due Date: Day-" + dueDay);
    }

    public void returnBook() {
        System.out.println("\n--- RETURN BOOK ---");
        if (issuedBooks.isEmpty()) {
            System.out.println("No issued books found.");
            return;
        }

        printActiveIssuedBooks();
        System.out.print("Enter Book ID to return: ");

        int bookId;
        try {
            bookId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
            return;
        }

        IssueRecord record = issuedBooks.findActiveRecordByBookId(bookId);
        if (record == null) {
            System.out.println("No active issue record for Book ID " + bookId + ".");
            return;
        }

        System.out.print("Enter return day number (default " + currentDayCount + "): ");
        String inputDay = scanner.nextLine().trim();

        int returnDay = currentDayCount;
        if (!inputDay.isEmpty()) {
            try {
                returnDay = Integer.parseInt(inputDay);
            } catch (NumberFormatException e) {
                System.out.println("Invalid day. Using current day: " + currentDayCount);
                returnDay = currentDayCount;
            }
        }

        if (returnDay < record.getIssueDayCount()) {
            returnDay = currentDayCount;
        }

        record.setReturnDayCount(returnDay);
        record.setReturnDate("Day-" + returnDay);
        record.setReturned(true);

        int overdueDays = returnDay - record.getDueDayCount();
        double fine = overdueDays > 0 ? overdueDays * FINE_PER_DAY : 0.0;
        record.setFineAmount(fine);

        Book book = bookHashTable.search(bookId);
        if (book != null) {
            book.setAvailable(true);
            bookHashTable.insert(book);
        }

        if (fine > 0) {
            System.out.println("Overdue by " + overdueDays + " day(s). Fine: Rs." + String.format("%.2f", fine));
        } else {
            System.out.println("Book returned on time. Fine: Rs.0.00");
        }
    }

    public void viewIssuedBooks() {
        IssueRecord[] records = issuedBooks.toArray();
        if (records.length == 0) {
            System.out.println("No issue records found.");
            return;
        }

        System.out.println("\n========== ISSUED BOOKS ==========");
        for (int i = 0; i < records.length; i++) {
            System.out.println((i + 1) + ". " + records[i]);
        }
    }

    public void calculateFine() {
        System.out.println("\n--- CALCULATE FINE ---");
        System.out.print("Enter active Book ID: ");

        int bookId;
        try {
            bookId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
            return;
        }

        IssueRecord record = issuedBooks.findActiveRecordByBookId(bookId);
        if (record == null) {
            System.out.println("No active issue record for this book.");
            return;
        }

        System.out.print("Enter day number for fine calculation (default " + currentDayCount + "): ");
        String inputDay = scanner.nextLine().trim();

        int dayForCalc = currentDayCount;
        if (!inputDay.isEmpty()) {
            try {
                dayForCalc = Integer.parseInt(inputDay);
            } catch (NumberFormatException e) {
                System.out.println("Invalid day input. Using current day: " + currentDayCount);
                dayForCalc = currentDayCount;
            }
        }

        if (dayForCalc > currentDayCount) {
            currentDayCount = dayForCalc;
        }

        int overdueDays = dayForCalc - record.getDueDayCount();
        double fine = overdueDays > 0 ? overdueDays * FINE_PER_DAY : 0.0;

        System.out.println("Book: " + record.getBook().getTitle());
        System.out.println("Due Day: Day-" + record.getDueDayCount());
        System.out.println("Calculation Day: Day-" + dayForCalc);
        System.out.println("Fine: Rs." + String.format("%.2f", fine));
    }

    public void displaySystemStatus() {
        System.out.println();
        System.out.println("========== SYSTEM STATUS ==========");
        System.out.println("Current System Day     : Day-" + currentDayCount);
        System.out.println("Books in Catalogue     : " + catalogue.getSize() + " (Singly Linked List)");
        System.out.println("Issue Records          : " + issuedBooks.getSize() + " (Doubly Linked List)");
        System.out.println("Pending Requests       : " + issueQueue.getSize() + " (Queue ADT)");
        System.out.println("Hash Table Entries     : " + bookHashTable.getSize()
            + " (Separate Chaining, capacity=" + bookHashTable.getCapacity() + ")");
        System.out.println("Next book ID           : " + nextBookId);
        System.out.println("Fine Rate              : Rs." + FINE_PER_DAY + " per day overdue");
        System.out.println("Loan Period            : " + LOAN_DAYS + " days");
        System.out.println("===================================");
    }

    private void loadSampleData() {
        String[][] sampleBooks = {
            {"Introduction to Algorithms", "Thomas H. Cormen", "Computer Science"},
            {"Clean Code", "Robert C. Martin", "Software Engineering"},
            {"The Pragmatic Programmer", "Andrew Hunt", "Software Engineering"},
            {"Design Patterns", "Gang of Four", "Computer Science"},
            {"Head First Java", "Kathy Sierra", "Programming"},
            {"Data Structures in Java", "John Hubbard", "Computer Science"}
        };

        for (String[] data : sampleBooks) {
            Book book = new Book(nextBookId++, data[0], data[1], data[2]);
            catalogue.addBook(book);
            bookHashTable.insert(book);
        }
    }

    private void printSearchResults(Book[] results) {
        if (results == null || results.length == 0) {
            System.out.println("No books found.");
            return;
        }

        for (int i = 0; i < results.length; i++) {
            System.out.println((i + 1) + ". " + results[i]);
        }
        System.out.println("Found " + results.length + " book(s).");
    }

    private void printActiveIssuedBooks() {
        IssueRecord[] records = issuedBooks.toArray();
        int count = 0;
        for (IssueRecord record : records) {
            if (!record.isReturned()) {
                count++;
                System.out.println(count + ". " + record);
            }
        }
        if (count == 0) {
            System.out.println("All books are already returned.");
        }
    }
}
