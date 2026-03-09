package structures;

public class IssueQueue {

    public static class IssueRequest {
        private final int requestId;
        private final int bookId;
        private final int studentId;
        private final String studentName;
        private final String issuedBy;
        private final String requestDate;

        public IssueRequest(int requestId, int bookId, int studentId, String studentName, String issuedBy, String requestDate) {
            this.requestId = requestId;
            this.bookId = bookId;
            this.studentId = studentId;
            this.studentName = studentName;
            this.issuedBy = issuedBy;
            this.requestDate = requestDate;
        }

        public int getRequestId() {
            return requestId;
        }

        public int getBookId() {
            return bookId;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getIssuedBy() {
            return issuedBy;
        }

        public String getRequestDate() {
            return requestDate;
        }
    }

    private static class QNode {
        IssueRequest request;
        QNode next;

        QNode(IssueRequest request) {
            this.request = request;
        }
    }

    private QNode front;
    private QNode rear;
    private int size;

    public void enqueue(IssueRequest request) {
        QNode node = new QNode(request);
        if (rear == null) {
            front = node;
            rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    public IssueRequest dequeue() {
        if (front == null) {
            return null;
        }

        IssueRequest request = front.request;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return request;
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

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}
