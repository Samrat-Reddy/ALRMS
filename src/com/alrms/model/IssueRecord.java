package com.alrms.model;

import java.time.LocalDate;

public class IssueRecord {

    private String memberID;
    private String bookID;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public IssueRecord(String memberID, String bookID) {
        setMemberID(memberID);
        setBookID(bookID);

        this.issueDate = LocalDate.now();
        this.returnDate = null;
    }

    private void setMemberID(String memberID) {
        if(memberID == null) {
            throw new IllegalArgumentException("Member ID cannot be null");
        }

        String trimmed = memberID.trim();
        if(trimmed.isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be empty");
        }

        this.memberID = trimmed;
    }

    private void setBookID(String bookID) {
        if(bookID == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }

        String trimmed = bookID.trim();
        if(trimmed.isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be empty");
        }

        this.bookID = trimmed;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getBookID() {
        return bookID;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void markReturned() {
        if(returnDate != null) {
            throw new IllegalStateException("Book already returned");
        }
        this.returnDate = LocalDate.now();
    }

    public boolean isActive() {
        return returnDate == null;
    }
}
