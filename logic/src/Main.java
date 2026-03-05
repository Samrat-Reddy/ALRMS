/**
 * Main.java
 * Entry Point — Automated Library Resource Management System (ALRMS)
 *
 * Provides a menu-driven console interface that loops until the user exits.
 * All features map directly to the backend DSA structures in services.LibrarySystem.java.
 *
 * DSA Structures Used:
 *  - Singly Linked List   → models.Book Catalogue
 *  - Doubly Linked List   → Issued models.Book Records
 *  - Queue ADT            → Issue Requests
 *  - Hash Table           → models.Book Lookup (Separate Chaining + Rehashing)
 *  - Linear Search        → Title / Author / Genre search
 *  - Binary Search        → Exact BookId search on sorted array
 *  - Merge Sort           → Primary catalogue sort
 *  - Bubble / Insertion / Selection / Quick Sort → Alternative sorts
 *
 * Fine Policy : Rs.5 per overdue day
 * Loan Period : 14 days
 */
import services.LibrarySystem;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // -------------------------------------------------------
        // Initialize the Library System (loads sample data)
        // -------------------------------------------------------
        LibrarySystem library = new LibrarySystem(scanner);

        System.out.println("============================================================");
        System.out.println("  Welcome to ALRMS — Automated Library Resource Management  ");
        System.out.println("============================================================");
        library.displaySystemStatus();

        boolean running = true;

        // -------------------------------------------------------
        // Main Menu Loop — runs until user selects Exit
        // -------------------------------------------------------
        while (running) {
            printMainMenu();
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {

                // =====================================================
                //  1. Add models.Book  →  Singly Linked List + Hash Table
                // =====================================================
                case "1":
                    library.addBook();
                    break;

                // =====================================================
                //  2. View Catalogue  →  Singly Linked List traversal
                // =====================================================
                case "2":
                    library.viewCatalogue();
                    break;

                // =====================================================
                //  3. Search models.Book  →  Linear Search / Hash / Binary Search
                // =====================================================
                case "3":
                    library.searchBook();
                    break;

                // =====================================================
                //  4. Sort Catalogue  →  Merge/Bubble/Insertion/Selection/Quick Sort
                // =====================================================
                case "4":
                    library.sortCatalogue();
                    break;

                // =====================================================
                //  5. Add Issue Request  →  Queue ADT (Enqueue)
                // =====================================================
                case "5":
                    library.addIssueRequest();
                    break;

                // =====================================================
                //  6. Process Issue Request  →  Queue ADT (Dequeue)
                //     models.Book issued to student → Doubly Linked List
                // =====================================================
                case "6":
                    library.processIssueRequest();
                    break;

                // =====================================================
                //  7. Return models.Book  →  Doubly Linked List + Fine Calc
                // =====================================================
                case "7":
                    library.returnBook();
                    break;

                // =====================================================
                //  8. View Issued Books  →  Doubly Linked List
                //     (forward/backward traversal, filter by type)
                // =====================================================
                case "8":
                    library.viewIssuedBooks();
                    break;

                // =====================================================
                //  9. Calculate Fine  →  Fine formula + Day simulation
                // =====================================================
                case "9":
                    library.calculateFine();
                    break;

                // =====================================================
                //  10. Manage Books  →  Remove models.Book from catalogue
                // =====================================================
                case "10":
                    printManageMenu();
                    String manageChoice = scanner.nextLine().trim();
                    if (manageChoice.equals("1")) {
                        library.addBook();
                    } else if (manageChoice.equals("2")) {
                        library.removeBook();
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;

                // =====================================================
                //  11. View Pending Issue Requests (Queue)
                // =====================================================
                case "11":
                    library.viewIssueRequests();
                    break;

                // =====================================================
                //  12. System Status
                // =====================================================
                case "12":
                    library.displaySystemStatus();
                    break;

                // =====================================================
                //  13. Advance System Day (simulate time)
                // =====================================================
                case "13":
                    library.advanceDay();
                    break;

                // =====================================================
                //  0. Exit
                // =====================================================
                case "0":
                    running = false;
                    System.out.println("\nThank you for using ALRMS. Goodbye!");
                    System.out.println("(All data structures have been garbage collected.)");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid menu option.");
            }

            if (running) {
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // -------------------------------------------------------
    // Print the main menu to console
    // -------------------------------------------------------
    private static void printMainMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     ===== ALRMS LIBRARY SYSTEM =====     ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  CATALOGUE & BOOKS                       ║");
        System.out.println("║   1.  Add models.Book                           ║");
        System.out.println("║   2.  View Catalogue                     ║");
        System.out.println("║   3.  Search models.Book                        ║");
        System.out.println("║   4.  Sort Catalogue                     ║");
        System.out.println("║   10. Manage Books (Add / Remove)        ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  ISSUE MANAGEMENT                        ║");
        System.out.println("║   5.  Add Issue Request                  ║");
        System.out.println("║   6.  Process Issue Request              ║");
        System.out.println("║   7.  Return models.Book                        ║");
        System.out.println("║   8.  View Issued Books                  ║");
        System.out.println("║   11. View Pending Issue Requests        ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  FINES & SYSTEM                          ║");
        System.out.println("║   9.  Calculate Fine                     ║");
        System.out.println("║   12. System Status                      ║");
        System.out.println("║   13. Advance System Day (simulate time) ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║   0.  Exit                               ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    // -------------------------------------------------------
    // Print the manage books sub-menu
    // -------------------------------------------------------
    private static void printManageMenu() {
        System.out.println("\n--- MANAGE BOOKS ---");
        System.out.println("  1. Add models.Book");
        System.out.println("  2. Remove models.Book");
        System.out.print("Choose option: ");
    }
}
