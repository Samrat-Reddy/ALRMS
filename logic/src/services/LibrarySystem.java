package services; /**
 * services.LibrarySystem.java
 * Service Layer — Orchestrates all DSA structures and algorithms.
 *
 * Responsibilities:
 *  - Maintains the models.Book Catalogue (Singly Linked List + Hash Table)
 *  - Manages Issued Books (Doubly Linked List)
 *  - Handles Issue Request Queue (Queue ADT)
 *  - Coordinates Search (Linear Search / Binary Search)
 *  - Coordinates Sort (Merge Sort and others)
 *  - Calculates Fines
 *  - Pre-loads sample data for demonstration
 *
 * Fine Policy: Rs. 5 per day after due date.
 */
import algorithms.SearchAlgorithms;
import algorithms.SortAlgorithms;
import models.Book;
import models.IssuedBookList;
import models.IssueRecord;
import models.Student;
import structures.BookLinkedList;
import structures.HashTable;
import structures.IssueQueue;

import java.util.Scanner;

public class LibrarySystem {

    // =====================================================
    //  DATA STRUCTURE INSTANCES
    // =====================================================

    // DSA: Singly Linked List — models.Book Catalogue
    private BookLinkedList catalogue;

    // DSA: Doubly Linked List — Issued models.Book Records
    private IssuedBookList issuedBooks;

    // DSA: Queue ADT — Pending Issue Requests
    private IssueQueue issueQueue;

    // DSA: Hash Table (Separate Chaining) — Fast models.Book Lookup
    private HashTable bookHashTable;

    // =====================================================
    //  COUNTERS FOR ID GENERATION
    // =====================================================
    private int nextBookId    = 100;
    private int nextRecordId  = 1;
    private int nextRequestId = 1;
    private int nextStudentId = 1001;

    // Simple "day counter" — simulates a calendar (days since system start)
    // Each issued book gets a day count; fine = (returnDay - dueDay) * 5 if positive
    private int currentDayCount = 1;  // Day 1 = system start

    // Fine rate per overdue day (in Rupees)
    private static final double FINE_PER_DAY = 5.0;
    // Standard loan period in days
    private static final int    LOAN_DAYS    = 14;

    // Scanner (passed from Main to avoid duplicate resource warnings)
    private Scanner scanner;

    // =====================================================
    //  CONSTRUCTOR
    // =====================================================
    public LibrarySystem(Scanner scanner) {
        this.catalogue      = new BookLinkedList();
        this.issuedBooks    = new IssuedBookList();
        this.issueQueue     = new IssueQueue();
        this.bookHashTable  = new HashTable();
        this.scanner        = scanner;
        loadSampleData();
    }

    // =====================================================
    //  FEATURE 1: Add models.Book (Manage Books — Add)
    // =====================================================
    public void addBook() {
        System.out.println("\n--- ADD NEW BOOK ---");
        System.out.print("Enter models.Book Title  : ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Author Name : ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter Genre       : ");
        String genre = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            System.out.println("Error: Title, Author, and Genre cannot be empty.");
            return;
        }

        int bookId = nextBookId++;
        Book newBook = new Book(bookId, title, author, genre);

        // Add to Singly Linked List (catalogue)
        catalogue.addBook(newBook);

        // Add to Hash Table for O(1) lookup
        bookHashTable.insert(newBook);

        System.out.println("Book added successfully.");
        System.out.println("models.Book ID assigned: " + bookId);
    }

    // =====================================================
    //  FEATURE 2: View Catalogue (Singly Linked List display)
    // =====================================================
    public void viewCatalogue() {
        Book[] books = catalogue.toArray();
        if (books.length == 0) {
            System.out.println("Catalogue is empty. No books available.");
            return;
        }
        System.out.println("\n========== LIBRARY CATALOGUE ==========");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
        System.out.println("Total books in catalogue: " + books.length);
        System.out.println("========================================");
    }

    // =====================================================
    //  FEATURE 3: Search models.Book
    // =====================================================
    public void searchBook() {
        System.out.println("\n--- SEARCH BOOK ---");
        System.out.println("Search by:");
        System.out.println("  1. Title   (Linear Search)");
        System.out.println("  2. Author  (Linear Search)");
        System.out.println("  3. Genre   (Linear Search)");
        System.out.println("  4. models.Book ID (Hash Table Lookup / Binary Search)");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().trim();

        if (catalogue.isEmpty()) {
            System.out.println("Catalogue is empty. No books to search.");
            return;
        }

        Book[] books = catalogue.toArray();

        switch (choice) {
            case "1": {
                System.out.print("Enter title keyword: ");
                String keyword = scanner.nextLine().trim();
                // DSA: Linear Search by title
                Book[] results = SearchAlgorithms.linearSearchByTitle(books, keyword);
                printSearchResults(results, keyword);
                break;
            }
            case "2": {
                System.out.print("Enter author keyword: ");
                String keyword = scanner.nextLine().trim();
                // DSA: Linear Search by author
                Book[] results = SearchAlgorithms.linearSearchByAuthor(books, keyword);
                printSearchResults(results, keyword);
                break;
            }
            case "3": {
                System.out.print("Enter genre keyword: ");
                String keyword = scanner.nextLine().trim();
                // DSA: Linear Search by genre
                Book[] results = SearchAlgorithms.linearSearchByGenre(books, keyword);
                printSearchResults(results, keyword);
                break;
            }
            case "4": {
                System.out.print("Enter models.Book ID: ");
                String idStr = scanner.nextLine().trim();
                try {
                    int bookId = Integer.parseInt(idStr);

                    // DSA: Hash Table Lookup — O(1) average
                    Book hashResult = bookHashTable.search(bookId);
                    if (hashResult != null) {
                        System.out.println("\n[Hash Table Lookup] models.Book found:");
                        System.out.println(hashResult.toString());
                    } else {
                        // Fallback: Binary Search on sorted array — O(log n)
                        System.out.println("[Hash miss] Trying Binary Search on sorted catalogue...");
                        Book[] sortedBooks = catalogue.toArray();
                        SortAlgorithms.mergeSortById(sortedBooks);
                        Book bsResult = SearchAlgorithms.binarySearchById(sortedBooks, bookId);
                        if (bsResult != null) {
                            System.out.println("[Binary Search] models.Book found:");
                            System.out.println(bsResult.toString());
                        } else {
                            System.out.println("models.Book with ID " + bookId + " not found.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid models.Book ID. Please enter a number.");
                }
                break;
            }
            default:
                System.out.println("Invalid option.");
        }
    }

    // =====================================================
    //  FEATURE 4: Sort Catalogue
    // =====================================================
    public void sortCatalogue() {
        System.out.println("\n--- SORT CATALOGUE ---");
        System.out.println("Sort using:");
        System.out.println("  1. Merge Sort      — by Title    (O(n log n), Stable)  [RECOMMENDED]");
        System.out.println("  2. Bubble Sort     — by Title    (O(n²))");
        System.out.println("  3. Insertion Sort  — by Author   (O(n²))");
        System.out.println("  4. Selection Sort  — by models.Book ID  (O(n²))");
        System.out.println("  5. Quick Sort      — by Genre    (O(n log n) avg)");
        System.out.print("Choose sort option: ");

        String choice = scanner.nextLine().trim();

        if (catalogue.isEmpty()) {
            System.out.println("Catalogue is empty. Nothing to sort.");
            return;
        }

        Book[] books = catalogue.toArray();

        switch (choice) {
            case "1":
                // DSA: Merge Sort — divide and conquer
                SortAlgorithms.mergeSortByTitle(books);
                break;
            case "2":
                // DSA: Bubble Sort — adjacent swaps
                SortAlgorithms.bubbleSortByTitle(books);
                break;
            case "3":
                // DSA: Insertion Sort — build sorted portion
                SortAlgorithms.insertionSortByAuthor(books);
                break;
            case "4":
                // DSA: Selection Sort — find minimum each pass
                SortAlgorithms.selectionSortById(books);
                break;
            case "5":
                // DSA: Quick Sort — pivot partitioning
                SortAlgorithms.quickSortByGenre(books);
                break;
            default:
                System.out.println("Invalid option. Defaulting to Merge Sort by Title.");
                SortAlgorithms.mergeSortByTitle(books);
        }

        // Rebuild the linked list with sorted order
        catalogue.fromArray(books);
        printSortedBooks(books);
        System.out.println("Catalogue order updated.");
    }

    // =====================================================
    //  FEATURE 5: Add Issue Request (Queue Enqueue)
    // =====================================================
    public void addIssueRequest() {
        System.out.println("\n--- ADD ISSUE REQUEST ---");

        // Show available books
        if (catalogue.isEmpty()) {
            System.out.println("No books in the catalogue to request.");
            return;
        }

        System.out.print("Enter models.Book ID to request : ");
        String bookIdStr = scanner.nextLine().trim();
        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid models.Book ID.");
            return;
        }

        // Check book exists in hash table
        Book book = bookHashTable.search(bookId);
        if (book == null) {
            System.out.println("models.Book with ID " + bookId + " not found in catalogue.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Sorry, book \"" + book.getTitle() + "\" is currently not available.");
            System.out.println("You can still add a request — it will be processed when returned.");
        }

        System.out.print("Enter models.Student Name         : ");
        String studentName = scanner.nextLine().trim();

        System.out.print("Issue as (1=STUDENT / 2=ADMIN): ");
        String typeChoice = scanner.nextLine().trim();
        String issuedBy   = typeChoice.equals("2") ? "ADMIN" : "STUDENT";

        int  reqId       = nextRequestId++;
        int  studentId   = nextStudentId++;
        String reqDate   = "Day-" + currentDayCount;

        IssueQueue.IssueRequest request = new IssueQueue.IssueRequest(
            reqId, bookId, studentId, studentName, issuedBy, reqDate, currentDayCount
        );

        // DSA: Queue Enqueue — add to rear (FIFO)
        issueQueue.enqueue(request);
        System.out.println("Request ID " + reqId + " has been added to the queue.");
        System.out.println("Current queue size: " + issueQueue.getSize());
    }

    // =====================================================
    //  FEATURE 6: Process Issue Request (Queue Dequeue)
    // =====================================================
    public void processIssueRequest() {
        System.out.println("\n--- PROCESS NEXT ISSUE REQUEST ---");

        if (issueQueue.isEmpty()) {
            System.out.println("No pending issue requests in the queue.");
            return;
        }

        // Show pending requests before processing
        printIssueQueue(issueQueue.toArray());

        System.out.print("Process next request in queue? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Processing cancelled.");
            return;
        }

        // DSA: Queue Dequeue — remove from front (FIFO)
        IssueQueue.IssueRequest request = issueQueue.dequeue();
        if (request == null) return;

        System.out.println("\nProcessing: " + request.toString());

        // Look up the book from hash table
        Book book = bookHashTable.search(request.getBookId());
        if (book == null) {
            System.out.println("Error: models.Book ID " + request.getBookId()
                + " no longer exists in catalogue. Request discarded.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Warning: models.Book \"" + book.getTitle()
                + "\" is currently NOT available. Adding to waitlist notice.");
            System.out.println("Request has been removed from queue. models.Student will be notified.");
            return;
        }

        // Create models.Student object
        Student student = new Student(
            request.getStudentId(),
            request.getStudentName(),
            request.getStudentName().toLowerCase().replace(" ", ".") + "@library.edu",
            "General"
        );

        // Issue the book
        int    issueDay = currentDayCount;
        int    dueDay   = issueDay + LOAN_DAYS;
        String issueDate = "Day-" + issueDay;
        String dueDate   = "Day-" + dueDay;

        int      recordId = nextRecordId++;
        IssueRecord record = new IssueRecord(
            recordId, book, student,
            request.getIssuedBy(),
            issueDate, dueDate,
            issueDay, dueDay
        );

        // DSA: Doubly Linked List — add to issued books list
        issuedBooks.addRecord(record);

        // Mark book as unavailable
        book.setAvailable(false);
        bookHashTable.insert(book);  // Update hash table entry

        System.out.println("\nmodels.Book issued successfully!");
        System.out.println("models.Book   : " + book.getTitle());
        System.out.println("models.Student: " + student.getName());
        System.out.println("IssueDate : " + issueDate);
        System.out.println("DueDate   : " + dueDate + " (return within " + LOAN_DAYS + " days)");
        System.out.println("Record ID : " + recordId);
    }

    // =====================================================
    //  FEATURE 7: Return models.Book
    // =====================================================
    public void returnBook() {
        System.out.println("\n--- RETURN BOOK ---");

        if (issuedBooks.isEmpty()) {
            System.out.println("No books are currently issued.");
            return;
        }

        // Show currently active (not returned) issues
        printActiveIssuedBooks();

        System.out.print("Enter models.Book ID to return: ");
        String bookIdStr = scanner.nextLine().trim();
        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid models.Book ID.");
            return;
        }

        // Find active record in doubly linked list
        IssueRecord record = issuedBooks.findActiveRecordByBookId(bookId);
        if (record == null) {
            System.out.println("No active issue record found for models.Book ID " + bookId + ".");
            return;
        }

        System.out.print("Enter return day count (current system day is " + currentDayCount + "): ");
        String dayStr = scanner.nextLine().trim();
        int returnDay;
        try {
            returnDay = Integer.parseInt(dayStr);
            if (returnDay < record.getIssueDayCount()) {
                System.out.println("Return day cannot be before issue day. Using current day.");
                returnDay = currentDayCount;
            }
        } catch (NumberFormatException e) {
            returnDay = currentDayCount;
            System.out.println("Invalid input. Using current day: " + returnDay);
        }

        String returnDate = "Day-" + returnDay;
        record.setReturnDate(returnDate);
        record.setReturnDayCount(returnDay);
        record.setReturned(true);

        // Calculate fine
        int overdueDays = returnDay - record.getDueDayCount();
        double fine = 0.0;
        if (overdueDays > 0) {
            fine = overdueDays * FINE_PER_DAY;
            System.out.println("\n*** OVERDUE NOTICE ***");
            System.out.println("Days overdue : " + overdueDays);
            System.out.println("Fine amount  : Rs." + fine);
        } else {
            System.out.println("models.Book returned on time. No fine.");
        }
        record.setFineAmount(fine);

        // Mark book as available again
        Book book = bookHashTable.search(bookId);
        if (book != null) {
            book.setAvailable(true);
            bookHashTable.insert(book);
        }

        System.out.println("\nmodels.Book returned successfully!");
        System.out.println("Title      : " + record.getBook().getTitle());
        System.out.println("models.Student    : " + record.getStudent().getName());
        System.out.println("ReturnDate : " + returnDate);
        System.out.println("Fine       : Rs." + String.format("%.2f", fine));
    }

    // =====================================================
    //  FEATURE 8: View Issued Books (Doubly Linked List)
    // =====================================================
    public void viewIssuedBooks() {
        System.out.println("\n--- VIEW ISSUED BOOKS ---");
        System.out.println("  1. All issued book records");
        System.out.println("  2. models.Student issued books");
        System.out.println("  3. Admin issued books");
        System.out.println("  4. Currently active (not returned) books");
        System.out.println("  5. History in reverse order (backward traversal)");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": printIssuedRecords(issuedBooks.toArray(), "ALL ISSUED BOOKS (DOUBLY LINKED LIST - FORWARD)"); break;
            case "2": printStudentIssuedBooks(); break;
            case "3": printAdminIssuedBooks(); break;
            case "4": printActiveIssuedBooks(); break;
            case "5": printIssuedRecords(issuedBooks.toReverseArray(), "ISSUED BOOKS (DOUBLY LINKED LIST - REVERSE)"); break;
            default:
                System.out.println("Invalid option. Showing all records.");
                printIssuedRecords(issuedBooks.toArray(), "ALL ISSUED BOOKS (DOUBLY LINKED LIST - FORWARD)");
        }
    }

    // =====================================================
    //  FEATURE 9: Calculate Fine
    // =====================================================
    public void calculateFine() {
        System.out.println("\n--- CALCULATE FINE ---");
        System.out.println("  1. Calculate fine for a specific models.Book ID (active issue)");
        System.out.println("  2. View fine for a specific Record ID (including returned)");
        System.out.println("  3. Advance system day (simulate time passing)");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": {
                System.out.print("Enter models.Book ID: ");
                String idStr = scanner.nextLine().trim();
                try {
                    int bookId = Integer.parseInt(idStr);
                    IssueRecord record = issuedBooks.findActiveRecordByBookId(bookId);
                    if (record == null) {
                        System.out.println("No active issue record for models.Book ID " + bookId + ".");
                        return;
                    }
                    int dueDay = record.getDueDayCount();
                    int overdueDays = currentDayCount - dueDay;
                    System.out.println("\nmodels.Book     : " + record.getBook().getTitle());
                    System.out.println("models.Student  : " + record.getStudent().getName());
                    System.out.println("Due Day  : Day-" + dueDay);
                    System.out.println("Today    : Day-" + currentDayCount);
                    if (overdueDays > 0) {
                        double fine = overdueDays * FINE_PER_DAY;
                        System.out.println("Overdue  : " + overdueDays + " day(s)");
                        System.out.println("Fine amount: Rs." + String.format("%.2f", fine)
                            + " (Rs." + FINE_PER_DAY + "/day)");
                    } else {
                        System.out.println("Status   : Not overdue. Days remaining: " + Math.abs(overdueDays));
                        System.out.println("Fine amount: Rs.0.00");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid models.Book ID.");
                }
                break;
            }
            case "2": {
                System.out.print("Enter Record ID: ");
                String idStr = scanner.nextLine().trim();
                try {
                    int recordId = Integer.parseInt(idStr);
                    IssueRecord record = issuedBooks.findRecordById(recordId);
                    if (record == null) {
                        System.out.println("Record ID " + recordId + " not found.");
                        return;
                    }
                    System.out.println("\nRecord   : " + record.toString());
                    System.out.println("Fine amount: Rs." + String.format("%.2f", record.getFineAmount()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Record ID.");
                }
                break;
            }
            case "3": {
                System.out.print("Advance by how many days? ");
                String daysStr = scanner.nextLine().trim();
                try {
                    int days = Integer.parseInt(daysStr);
                    if (days <= 0) {
                        System.out.println("Days must be positive.");
                        return;
                    }
                    currentDayCount += days;
                    System.out.println("System day advanced by " + days + " day(s).");
                    System.out.println("Current system day: Day-" + currentDayCount);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
                break;
            }
            default:
                System.out.println("Invalid option.");
        }
    }

    // =====================================================
    //  FEATURE: Remove models.Book from Catalogue (Manage Books — Remove)
    // =====================================================
    public void removeBook() {
        System.out.println("\n--- REMOVE BOOK FROM CATALOGUE ---");
        System.out.print("Enter models.Book ID to remove: ");
        String idStr = scanner.nextLine().trim();
        try {
            int bookId = Integer.parseInt(idStr);

            // Check if currently issued
            IssueRecord activeRecord = issuedBooks.findActiveRecordByBookId(bookId);
            if (activeRecord != null) {
                System.out.println("Cannot remove: models.Book \"" + activeRecord.getBook().getTitle()
                    + "\" is currently issued to " + activeRecord.getStudent().getName() + ".");
                return;
            }

            // Remove from Singly Linked List
            boolean removedFromList = catalogue.removeBook(bookId);

            // Remove from Hash Table
            if (removedFromList) {
                bookHashTable.delete(bookId);
                System.out.println("models.Book (ID: " + bookId + ") removed from catalogue and hash table.");
            } else {
                System.out.println("Book with ID " + bookId + " not found in catalogue.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid models.Book ID.");
        }
    }

    // =====================================================
    //  View Pending Issue Requests Queue
    // =====================================================
    public void viewIssueRequests() {
        printIssueQueue(issueQueue.toArray());
    }

    // =====================================================
    //  Advance System Day (quick access from main menu)
    // =====================================================
    public void advanceDay() {
        System.out.print("\nAdvance system by how many days? (Current: Day-" + currentDayCount + "): ");
        String input = scanner.nextLine().trim();
        try {
            int days = Integer.parseInt(input);
            if (days > 0) {
                currentDayCount += days;
                System.out.println("System day is now: Day-" + currentDayCount);
            } else {
                System.out.println("Enter a positive number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    // =====================================================
    //  DISPLAY SYSTEM STATUS
    // =====================================================
    public void displaySystemStatus() {
        System.out.println("\n========== SYSTEM STATUS ==========");
        System.out.println("Current System Day     : Day-" + currentDayCount);
        System.out.println("Books in Catalogue     : " + catalogue.getSize()
            + "  (Singly Linked List)");
        System.out.println("Issue Records          : " + issuedBooks.getSize()
            + "  (Doubly Linked List)");
        System.out.println("Pending Requests       : " + issueQueue.getSize()
            + "  (Queue ADT)");
        System.out.println("Hash Table Entries     : " + bookHashTable.getSize()
            + "  (Separate Chaining, capacity=" + bookHashTable.getCapacity() + ")");
        System.out.println("Next models.Book ID           : " + nextBookId);
        System.out.println("Fine Rate              : Rs." + FINE_PER_DAY + " per day overdue");
        System.out.println("Loan Period            : " + LOAN_DAYS + " days");
        System.out.println("====================================");
    }

    // =====================================================
    //  LOAD SAMPLE DATA
    // =====================================================
    private void loadSampleData() {
        // Add sample books
        String[][] sampleBooks = {
            {"Introduction to Algorithms",  "Thomas H. Cormen",  "Computer Science"},
            {"Clean Code",                  "Robert C. Martin",  "Software Engineering"},
            {"The Pragmatic Programmer",    "Andrew Hunt",       "Software Engineering"},
            {"Design Patterns",             "Gang of Four",      "Computer Science"},
            {"Head First Java",             "Kathy Sierra",      "Programming"},
            {"Data Structures in Java",     "John Hubbard",      "Computer Science"},
            {"Operating System Concepts",   "Silberschatz",      "Systems"},
            {"Database System Concepts",    "Abraham Silberschatz", "Databases"},
            {"Computer Networks",           "Andrew Tanenbaum",  "Networking"},
            {"Artificial Intelligence",     "Stuart Russell",    "AI/ML"}
        };

        for (String[] bookData : sampleBooks) {
            Book book = new Book(nextBookId, bookData[0], bookData[1], bookData[2]);
            catalogue.addBook(book);
            bookHashTable.insert(book);
            nextBookId++;
        }

        // Add sample issue request
        IssueQueue.IssueRequest req1 = new IssueQueue.IssueRequest(
            nextRequestId++, 100, nextStudentId++,
            "Alice Johnson", "STUDENT", "Day-1", 1
        );
        IssueQueue.IssueRequest req2 = new IssueQueue.IssueRequest(
            nextRequestId++, 103, nextStudentId++,
            "Bob Smith", "ADMIN", "Day-1", 1
        );
        issueQueue.enqueue(req1);
        issueQueue.enqueue(req2);
    }

    private void printSearchResults(Book[] results, String searchTerm) {
        if (results == null || results.length == 0) {
            System.out.println("No books found matching: \"" + searchTerm + "\"");
            return;
        }
        System.out.println("\n--- Search Results for: \"" + searchTerm + "\" ---");
        for (int i = 0; i < results.length; i++) {
            System.out.println((i + 1) + ". " + results[i]);
        }
        System.out.println("Found " + results.length + " book(s).");
    }

    private void printSortedBooks(Book[] books) {
        if (books == null || books.length == 0) {
            System.out.println("No books to display.");
            return;
        }
        System.out.println("\n--- Sorted Catalogue ---");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
    }

    private void printIssueQueue(IssueQueue.IssueRequest[] requests) {
        if (requests == null || requests.length == 0) {
            System.out.println("Issue queue is empty. No pending requests.");
            return;
        }
        System.out.println("\n========== PENDING ISSUE REQUESTS (QUEUE - FIFO) ==========");
        for (int i = 0; i < requests.length; i++) {
            System.out.println((i + 1) + ". " + requests[i]);
        }
        System.out.println("Total pending requests: " + requests.length);
        System.out.println("============================================================");
    }

    private void printIssuedRecords(IssueRecord[] records, String header) {
        if (records == null || records.length == 0) {
            System.out.println("No issue records found.");
            return;
        }
        System.out.println("\n========== " + header + " ==========");
        for (int i = 0; i < records.length; i++) {
            System.out.println((i + 1) + ". " + records[i]);
        }
        if (header.contains("FORWARD")) {
            System.out.println("Total records: " + records.length);
        }
        System.out.println("=================================================================");
    }

    private void printStudentIssuedBooks() {
        IssueRecord[] records = issuedBooks.toArray();
        if (records.length == 0) {
            System.out.println("No issue records found.");
            return;
        }
        System.out.println("\n========== STUDENT ISSUED BOOKS ==========");
        int count = 0;
        for (int i = 0; i < records.length; i++) {
            if (records[i].getIssuedBy().equalsIgnoreCase("STUDENT")) {
                count++;
                System.out.println(count + ". " + records[i]);
            }
        }
        if (count == 0) System.out.println("No student issued books found.");
        System.out.println("Total student issued: " + count);
        System.out.println("==========================================");
    }

    private void printAdminIssuedBooks() {
        IssueRecord[] records = issuedBooks.toArray();
        if (records.length == 0) {
            System.out.println("No issue records found.");
            return;
        }
        System.out.println("\n========== ADMIN ISSUED BOOKS ==========");
        int count = 0;
        for (int i = 0; i < records.length; i++) {
            if (records[i].getIssuedBy().equalsIgnoreCase("ADMIN")) {
                count++;
                System.out.println(count + ". " + records[i]);
            }
        }
        if (count == 0) System.out.println("No admin issued books found.");
        System.out.println("Total admin issued: " + count);
        System.out.println("=========================================");
    }

    private void printActiveIssuedBooks() {
        IssueRecord[] records = issuedBooks.toArray();
        if (records.length == 0) {
            System.out.println("No issue records found.");
            return;
        }
        System.out.println("\n========== CURRENTLY ISSUED (NOT RETURNED) BOOKS ==========");
        int count = 0;
        for (int i = 0; i < records.length; i++) {
            if (!records[i].isReturned()) {
                count++;
                System.out.println(count + ". " + records[i]);
            }
        }
        if (count == 0) System.out.println("All books have been returned.");
        System.out.println("============================================================");
    }
}
