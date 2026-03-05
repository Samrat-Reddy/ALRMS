package structures;

/**
 * structures.IssueQueue.java
 * DSA Concept: Queue ADT (First-In-First-Out)
 *
 * Issue requests from students/admins are stored in a Queue.
 * The first request submitted is the first one to be processed (FIFO).
 * Implemented manually using a linked-list-based approach (no Java collections).
 *
 * Queue Operations:
 *   enqueue(request) → add to rear
 *   dequeue()        → remove from front
 *   peek()           → see front without removing
 *   isEmpty()        → check if empty
 *
 * Visual:
 *   FRONT -> [Req1] -> [Req2] -> [Req3] -> REAR
 *   dequeue from FRONT, enqueue at REAR
 */
public class IssueQueue {

    // -------------------------------------------------------
    // Inner class representing a single Issue Request
    // -------------------------------------------------------
    public static class IssueRequest {
        private int    requestId;
        private int    bookId;
        private int    studentId;
        private String studentName;
        private String issuedBy;      // "STUDENT" or "ADMIN"
        private String requestDate;
        private int    requestDayCount; // numeric day for fine logic

        public IssueRequest(int requestId, int bookId, int studentId,
                            String studentName, String issuedBy,
                            String requestDate, int requestDayCount) {
            this.requestId       = requestId;
            this.bookId          = bookId;
            this.studentId       = studentId;
            this.studentName     = studentName;
            this.issuedBy        = issuedBy;
            this.requestDate     = requestDate;
            this.requestDayCount = requestDayCount;
        }

        public int    getRequestId()       { return requestId; }
        public int    getBookId()          { return bookId; }
        public int    getStudentId()       { return studentId; }
        public String getStudentName()     { return studentName; }
        public String getIssuedBy()        { return issuedBy; }
        public String getRequestDate()     { return requestDate; }
        public int    getRequestDayCount() { return requestDayCount; }

        @Override
        public String toString() {
            return String.format(
                "[ReqID: %d | BookID: %d | StudentID: %d | models.Student: %-15s | IssuedBy: %s | Date: %s]",
                requestId, bookId, studentId, studentName, issuedBy, requestDate
            );
        }
    }

    // -------------------------------------------------------
    // Inner Node for the queue's internal linked list
    // -------------------------------------------------------
    private static class QNode {
        IssueRequest request;
        QNode next;

        QNode(IssueRequest request) {
            this.request = request;
            this.next    = null;
        }
    }

    private QNode front;   // Points to the front of the queue
    private QNode rear;    // Points to the rear of the queue
    private int   size;    // Current number of elements

    public IssueQueue() {
        front = null;
        rear  = null;
        size  = 0;
    }

    // -------------------------------------------------------
    // Enqueue: Add a request at the rear  O(1)
    // -------------------------------------------------------
    public void enqueue(IssueRequest request) {
        QNode newNode = new QNode(request);
        if (rear == null) {
            // Queue is empty
            front = newNode;
            rear  = newNode;
        } else {
            rear.next = newNode;
            rear      = newNode;
        }
        size++;
    }

    // -------------------------------------------------------
    // Dequeue: Remove and return request from the front  O(1)
    // -------------------------------------------------------
    public IssueRequest dequeue() {
        if (front == null) return null;
        IssueRequest request = front.request;
        front = front.next;
        if (front == null) {
            // Queue became empty
            rear = null;
        }
        size--;
        return request;
    }

    // -------------------------------------------------------
    // Peek: View the front request without removing  O(1)
    // -------------------------------------------------------
    public IssueRequest peek() {
        if (front == null) return null;
        return front.request;
    }

    // -------------------------------------------------------
    // Display all pending requests in the queue  O(n)
    // -------------------------------------------------------
    public void displayAll() {
        // Intentionally silent. UI output is handled in service/UI layer.
    }

    public IssueRequest[] toArray() {
        IssueRequest[] arr = new IssueRequest[size];
        QNode current = front;
        int i = 0;
        while (current != null) {
            arr[i++] = current.request;
            current = current.next;
        }
        return arr;
    }

    public boolean isEmpty() { return front == null; }
    public int     getSize() { return size; }
}
