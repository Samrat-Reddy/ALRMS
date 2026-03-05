package models;

/**
 * models.IssuedBookList.java
 * DSA Concept: Doubly Linked List
 *
 * All issued book records are stored as a Doubly Linked List.
 * Each node holds an models.IssueRecord, a reference to the NEXT node,
 * and a reference to the PREVIOUS node.
 * This allows efficient forward and backward traversal,
 * which is useful when navigating issue history.
 *
 * Structure:
 *   null <- [prev|Record|next] <-> [prev|Record|next] <-> [prev|Record|next] -> null
 *           HEAD                                           TAIL
 */
public class IssuedBookList {

    // -------------------------------------------------------
    // Inner Node class for Doubly Linked List
    // -------------------------------------------------------
    private static class DNode {
        IssueRecord record;
        DNode next;
        DNode prev;

        DNode(IssueRecord record) {
            this.record = record;
            this.next   = null;
            this.prev   = null;
        }
    }

    private DNode head;   // First node
    private DNode tail;   // Last node (for O(1) insertion at end)
    private int   size;

    public IssuedBookList() {
        head = null;
        tail = null;
        size = 0;
    }

    // -------------------------------------------------------
    // Add a new issue record at the tail  O(1)
    // -------------------------------------------------------
    public void addRecord(IssueRecord record) {
        DNode newNode = new DNode(record);
        if (tail == null) {
            // List is empty
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next    = newNode;
            tail         = newNode;
        }
        size++;
    }

    // -------------------------------------------------------
    // Find an active (not returned) record by bookId  O(n)
    // -------------------------------------------------------
    public IssueRecord findActiveRecordByBookId(int bookId) {
        DNode current = head;
        while (current != null) {
            if (current.record.getBook().getBookId() == bookId
                    && !current.record.isReturned()) {
                return current.record;
            }
            current = current.next;
        }
        return null;
    }

    // -------------------------------------------------------
    // Find any record by recordId  O(n)
    // -------------------------------------------------------
    public IssueRecord findRecordById(int recordId) {
        DNode current = head;
        while (current != null) {
            if (current.record.getRecordId() == recordId) {
                return current.record;
            }
            current = current.next;
        }
        return null;
    }

    // -------------------------------------------------------
    // Display ALL issue records (forward traversal)  O(n)
    // -------------------------------------------------------
    public void displayAll() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    // -------------------------------------------------------
    // Display only STUDENT issued books  O(n)
    // -------------------------------------------------------
    public void displayStudentIssued() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    // -------------------------------------------------------
    // Display only ADMIN issued books  O(n)
    // -------------------------------------------------------
    public void displayAdminIssued() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    // -------------------------------------------------------
    // Display all ACTIVE (not yet returned) records  O(n)
    // -------------------------------------------------------
    public void displayActiveRecords() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    // -------------------------------------------------------
    // Display in REVERSE order (backward traversal) — demonstrates doubly linked list feature  O(n)
    // -------------------------------------------------------
    public void displayReverse() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    public IssueRecord[] toArray() {
        IssueRecord[] arr = new IssueRecord[size];
        DNode current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.record;
            current = current.next;
        }
        return arr;
    }

    public IssueRecord[] toReverseArray() {
        IssueRecord[] arr = new IssueRecord[size];
        DNode current = tail;
        int i = 0;
        while (current != null) {
            arr[i++] = current.record;
            current = current.prev;
        }
        return arr;
    }

    public int getSize()   { return size; }
    public boolean isEmpty() { return head == null; }
}
