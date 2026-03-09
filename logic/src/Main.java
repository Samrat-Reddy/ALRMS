import services.LibrarySystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibrarySystem library = new LibrarySystem(scanner);

        printWelcomeBanner();
        library.displaySystemStatus();

        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    library.viewCatalogue();
                    break;
                case "2":
                    library.searchBook();
                    break;
                case "3":
                    library.sortCatalogue();
                    break;
                case "4":
                    library.addBook();
                    break;
                case "5":
                    library.addIssueRequest();
                    break;
                case "6":
                    library.processIssueRequest();
                    break;
                case "7":
                    library.returnBook();
                    break;
                case "8":
                    library.viewIssuedBooks();
                    break;
                case "9":
                    library.calculateFine();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting ALRMS. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (running) {
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void printWelcomeBanner() {
        System.out.println("==============================================================");
        System.out.println("  Welcome to ALRMS - Automated Library Resource Management");
        System.out.println("==============================================================");
    }

    private static void printMainMenu() {
        System.out.println("\n+===========================================================+");
        System.out.println("|        ===== ALRMS LIBRARY SYSTEM =====                  |");
        System.out.println("+===========================================================+");
        System.out.println("| CATALOGUE & BOOKS                                         |");
        System.out.println("|  1. View Catalogue                                        |");
        System.out.println("|  2. Search Book                                           |");
        System.out.println("|  3. Sort Catalogue                                        |");
        System.out.println("|  4. Add Book                                              |");
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("| ISSUE MANAGEMENT                                          |");
        System.out.println("|  5. Issue Request                                         |");
        System.out.println("|  6. Process Issue Request                                 |");
        System.out.println("|  7. Return Book                                           |");
        System.out.println("|  8. View Issued Books                                     |");
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("| FINES                                                     |");
        System.out.println("|  9. Calculate Fine                                        |");
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("|  0. Exit                                                  |");
        System.out.println("+===========================================================+");
    }
}
