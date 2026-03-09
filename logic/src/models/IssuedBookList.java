package models;

public class IssuedBookList {
    private static class DNode {
        IssueRecord record;
        DNode prev;
        DNode next;

        DNode(IssueRecord record) {
            this.record = record;
        }
    }

    private DNode head;
    private DNode tail;
    private int size;

    public void addRecord(IssueRecord record) {
        DNode node = new DNode(record);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public IssueRecord findActiveRecordByBookId(int bookId) {
        DNode current = head;
        while (current != null) {
            if (current.record.getBook().getBookId() == bookId && !current.record.isReturned()) {
                return current.record;
            }
            current = current.next;
        }
        return null;
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

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
