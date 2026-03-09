package algorithms;

import models.Book;

public class SortAlgorithms {

    public static void mergeSortByTitle(Book[] books) {
        if (books == null || books.length <= 1) {
            return;
        }
        mergeSort(books, 0, books.length - 1);
    }

    private static void mergeSort(Book[] books, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(books, left, mid);
        mergeSort(books, mid + 1, right);
        merge(books, left, mid, right);
    }

    private static void merge(Book[] books, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        Book[] leftArray = new Book[leftSize];
        Book[] rightArray = new Book[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = books[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = books[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i].getTitle().compareToIgnoreCase(rightArray[j].getTitle()) <= 0) {
                books[k++] = leftArray[i++];
            } else {
                books[k++] = rightArray[j++];
            }
        }

        while (i < leftSize) {
            books[k++] = leftArray[i++];
        }

        while (j < rightSize) {
            books[k++] = rightArray[j++];
        }
    }
}
