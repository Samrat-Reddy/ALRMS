package algorithms;

import models.Book;

/**
 * algorithms.SortAlgorithms.java
 * DSA Concepts: Bubble Sort, Insertion Sort, Selection Sort, Merge Sort, Quick Sort
 *
 * All sorting methods operate on models.Book[] arrays.
 * Sorting can be done by: bookId, title, or author.
 *
 * Complexities:
 *   Bubble Sort    O(n²) average/worst, O(n) best (with optimization)
 *   Insertion Sort O(n²) average/worst, O(n) best
 *   Selection Sort O(n²) all cases
 *   Merge Sort     O(n log n) all cases — STABLE, RECOMMENDED for catalogue
 *   Quick Sort     O(n log n) average, O(n²) worst — IN-PLACE
 */
public class SortAlgorithms {

    // -------------------------------------------------------
    // BUBBLE SORT by Title (ascending)
    // O(n²) — repeatedly swaps adjacent elements if out of order
    // Optimization: early exit if no swaps occurred (already sorted)
    // -------------------------------------------------------
    public static void bubbleSortByTitle(Book[] books) {
        if (books == null || books.length <= 1) return;
        int n = books.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (books[j].getTitle().compareToIgnoreCase(books[j + 1].getTitle()) > 0) {
                    // Swap
                    Book temp   = books[j];
                    books[j]    = books[j + 1];
                    books[j + 1] = temp;
                    swapped     = true;
                }
            }
            if (!swapped) break;  // Optimization: list already sorted
        }
    }

    // -------------------------------------------------------
    // INSERTION SORT by Author (ascending)
    // O(n²) — builds sorted portion by inserting each element in place
    // Efficient for small or nearly-sorted arrays
    // -------------------------------------------------------
    public static void insertionSortByAuthor(Book[] books) {
        if (books == null || books.length <= 1) return;
        int n = books.length;

        for (int i = 1; i < n; i++) {
            Book key = books[i];
            int  j   = i - 1;
            // Shift elements greater than key to the right
            while (j >= 0 && books[j].getAuthor().compareToIgnoreCase(key.getAuthor()) > 0) {
                books[j + 1] = books[j];
                j--;
            }
            books[j + 1] = key;
        }
    }

    // -------------------------------------------------------
    // SELECTION SORT by BookId (ascending)
    // O(n²) — finds the minimum element and places it at the front
    // Makes exactly n-1 swaps regardless of input
    // -------------------------------------------------------
    public static void selectionSortById(Book[] books) {
        if (books == null || books.length <= 1) return;
        int n = books.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (books[j].getBookId() < books[minIdx].getBookId()) {
                    minIdx = j;
                }
            }
            // Swap minimum with current position
            if (minIdx != i) {
                Book temp     = books[i];
                books[i]      = books[minIdx];
                books[minIdx] = temp;
            }
        }
    }

    // -------------------------------------------------------
    // MERGE SORT by Title (ascending) — PRIMARY SORT for catalogue
    // O(n log n) — divide and conquer, stable sort
    // Splits array in half, sorts each half, then merges them
    // -------------------------------------------------------
    public static void mergeSortByTitle(Book[] books) {
        if (books == null || books.length <= 1) return;
        mergeSortHelper(books, 0, books.length - 1);
    }

    // Recursive helper: sorts books[left..right]
    private static void mergeSortHelper(Book[] books, int left, int right) {
        if (left >= right) return;  // Base case: single element

        int mid = left + (right - left) / 2;

        mergeSortHelper(books, left, mid);        // Sort left half
        mergeSortHelper(books, mid + 1, right);   // Sort right half
        merge(books, left, mid, right);           // Merge sorted halves
    }

    // Merges two sorted halves books[left..mid] and books[mid+1..right]
    private static void merge(Book[] books, int left, int mid, int right) {
        int leftSize  = mid - left + 1;
        int rightSize = right - mid;

        // Temporary arrays
        Book[] leftArr  = new Book[leftSize];
        Book[] rightArr = new Book[rightSize];

        for (int i = 0; i < leftSize;  i++) leftArr[i]  = books[left + i];
        for (int j = 0; j < rightSize; j++) rightArr[j] = books[mid + 1 + j];

        int i = 0, j = 0, k = left;

        // Merge: pick smaller element from left or right
        while (i < leftSize && j < rightSize) {
            if (leftArr[i].getTitle().compareToIgnoreCase(rightArr[j].getTitle()) <= 0) {
                books[k++] = leftArr[i++];
            } else {
                books[k++] = rightArr[j++];
            }
        }
        // Copy remaining elements
        while (i < leftSize)  books[k++] = leftArr[i++];
        while (j < rightSize) books[k++] = rightArr[j++];
    }

    // -------------------------------------------------------
    // MERGE SORT by BookId (ascending) — used after rehash/binary search
    // Same divide-and-conquer logic but compares bookId
    // -------------------------------------------------------
    public static void mergeSortById(Book[] books) {
        if (books == null || books.length <= 1) return;
        mergeSortByIdHelper(books, 0, books.length - 1);
    }

    private static void mergeSortByIdHelper(Book[] books, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortByIdHelper(books, left, mid);
        mergeSortByIdHelper(books, mid + 1, right);
        mergeById(books, left, mid, right);
    }

    private static void mergeById(Book[] books, int left, int mid, int right) {
        int leftSize  = mid - left + 1;
        int rightSize = right - mid;

        Book[] leftArr  = new Book[leftSize];
        Book[] rightArr = new Book[rightSize];

        for (int i = 0; i < leftSize;  i++) leftArr[i]  = books[left + i];
        for (int j = 0; j < rightSize; j++) rightArr[j] = books[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArr[i].getBookId() <= rightArr[j].getBookId()) {
                books[k++] = leftArr[i++];
            } else {
                books[k++] = rightArr[j++];
            }
        }
        while (i < leftSize)  books[k++] = leftArr[i++];
        while (j < rightSize) books[k++] = rightArr[j++];
    }

    // -------------------------------------------------------
    // QUICK SORT by Genre (ascending)
    // O(n log n) average — in-place divide using pivot element
    // Worst case O(n²) when pivot is always min/max
    // -------------------------------------------------------
    public static void quickSortByGenre(Book[] books) {
        if (books == null || books.length <= 1) return;
        quickSortHelper(books, 0, books.length - 1);
    }

    private static void quickSortHelper(Book[] books, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(books, low, high);
            quickSortHelper(books, low, pivotIndex - 1);   // Sort left of pivot
            quickSortHelper(books, pivotIndex + 1, high);  // Sort right of pivot
        }
    }

    // Partition: puts pivot in correct position, smaller left, larger right
    private static int partition(Book[] books, int low, int high) {
        Book   pivot    = books[high];  // Last element as pivot
        int    i        = low - 1;      // Index of smaller element

        for (int j = low; j < high; j++) {
            if (books[j].getGenre().compareToIgnoreCase(pivot.getGenre()) <= 0) {
                i++;
                Book temp  = books[i];
                books[i]   = books[j];
                books[j]   = temp;
            }
        }
        // Place pivot in correct position
        Book temp       = books[i + 1];
        books[i + 1]    = books[high];
        books[high]     = temp;
        return i + 1;
    }

    // -------------------------------------------------------
    // Display a sorted array to console
    // -------------------------------------------------------
    public static void printSortedBooks(Book[] books) {
        // Intentionally silent. UI output is handled in service/UI layer.
    }
}
