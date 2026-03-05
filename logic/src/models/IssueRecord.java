package models;

/**
 * models.IssueRecord.java
 * Represents a record of a book being issued to a student or admin.
 * Stores issue date, due date, return date, and the fine amount.
 * Used as the data node in the models.IssuedBookList (Doubly Linked List).
 */
public class IssueRecord {
    private int    recordId;
    private Book   book;
    private Student student;
    private String issuedBy;       // "STUDENT" or "ADMIN"
    private String issueDate;      // Format: DD/MM/YYYY (stored as string for simplicity)
    private String dueDate;
    private String returnDate;     // null until returned
    private double fineAmount;
    private boolean returned;

    // Simple day counter (days since epoch, for fine calculation)
    private int issueDayCount;
    private int dueDayCount;
    private int returnDayCount;

    public IssueRecord(int recordId, Book book, Student student,
                       String issuedBy, String issueDate, String dueDate,
                       int issueDayCount, int dueDayCount) {
        this.recordId      = recordId;
        this.book          = book;
        this.student       = student;
        this.issuedBy      = issuedBy;
        this.issueDate     = issueDate;
        this.dueDate       = dueDate;
        this.returnDate    = null;
        this.fineAmount    = 0.0;
        this.returned      = false;
        this.issueDayCount = issueDayCount;
        this.dueDayCount   = dueDayCount;
        this.returnDayCount = 0;
    }

    // Getters
    public int     getRecordId()       { return recordId; }
    public Book    getBook()           { return book; }
    public Student getStudent()        { return student; }
    public String  getIssuedBy()       { return issuedBy; }
    public String  getIssueDate()      { return issueDate; }
    public String  getDueDate()        { return dueDate; }
    public String  getReturnDate()     { return returnDate; }
    public double  getFineAmount()     { return fineAmount; }
    public boolean isReturned()        { return returned; }
    public int     getIssueDayCount()  { return issueDayCount; }
    public int     getDueDayCount()    { return dueDayCount; }
    public int     getReturnDayCount() { return returnDayCount; }

    // Setters
    public void setReturnDate(String returnDate)    { this.returnDate = returnDate; }
    public void setFineAmount(double fineAmount)    { this.fineAmount = fineAmount; }
    public void setReturned(boolean returned)       { this.returned = returned; }
    public void setReturnDayCount(int returnDayCount) { this.returnDayCount = returnDayCount; }

    @Override
    public String toString() {
        return String.format(
            "[RecordID: %d | models.Book: \"%s\" | models.Student: %s | IssuedBy: %s | IssueDate: %s | DueDate: %s | Returned: %s | Fine: Rs.%.2f]",
            recordId,
            book.getTitle(),
            student.getName(),
            issuedBy,
            issueDate,
            dueDate,
            returned ? (returnDate != null ? returnDate : "Yes") : "No",
            fineAmount
        );
    }
}
