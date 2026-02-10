package com.alrms.service;

import com.alrms.model.Book;
import com.alrms.model.Member;
import com.alrms.model.IssueRecord;

import java.util.ArrayList;

public class LibraryManager {

    /* -------------------- BOOK MANAGEMENT (MODULE 1) -------------------- */

    private ArrayList<Book> books = new ArrayList<>();

    // Add new book
    public boolean addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(book.getBookId())) {
                return false; // duplicate book ID
            }
        }
        books.add(book);
        return true;
    }

    // Search book by ID
    public Book searchBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }

        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(bookId.trim())) {
                return b;
            }
        }
        return null;
    }

    // Check book availability
    public boolean isBookAvailable(String bookId) {
        Book book = searchBook(bookId);
        return book != null && book.getAvailableCopies() > 0;
    }

    /* -------------------- MEMBER MANAGEMENT (MODULE 2) -------------------- */

    private ArrayList<Member> members = new ArrayList<>();
    private static final int MAX_ISSUE_LIMIT = 3;

    // Add member
    public boolean addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if (findMember(member.getMemberID()) != null) {
            return false; // duplicate member
        }

        members.add(member);
        return true;
    }

    // Find member by ID
    public Member findMember(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }

        String trimmedId = memberId.trim();

        for (Member m : members) {
            if (m.getMemberID().equalsIgnoreCase(trimmedId)) {
                return m;
            }
        }
        return null;
    }

    // Check if member can issue more books
    public boolean canMemberIssue(String memberId) {
        Member member = findMember(memberId);
        return member != null && member.getIssuedBookCount() < MAX_ISSUE_LIMIT;
    }

    /* -------------------- ISSUE MANAGEMENT (MODULE 3) -------------------- */

    private ArrayList<IssueRecord> issueRecords = new ArrayList<>();

    // General permission check
    public boolean canIssue(String memberId, String bookId) {
        Member member = findMember(memberId);
        Book book = searchBook(bookId);

        if (member == null || book == null) {
            return false;
        }

        if (book.getAvailableCopies() <= 0) {
            return false;
        }

        return canMemberIssue(memberId);
    }

    // Find active issue record
    public IssueRecord findActiveIssueRecord(String memberId, String bookId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }

        String mId = memberId.trim();
        String bId = bookId.trim();

        for (IssueRecord record : issueRecords) {
            if (record.getMemberID().equalsIgnoreCase(mId)
                    && record.getBookID().equalsIgnoreCase(bId)
                    && record.isActive()) {
                return record;
            }
        }
        return null;
    }

    // Check duplicate issue
    public boolean isBookAlreadyIssued(String memberId, String bookId) {
        return findActiveIssueRecord(memberId, bookId) != null;
    }

    // ISSUE BOOK
    public boolean issueBook(String memberId, String bookId) {

        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }

        String mId = memberId.trim();
        String bId = bookId.trim();

        if (!canIssue(mId, bId)) {
            return false;
        }

        if (isBookAlreadyIssued(mId, bId)) {
            return false;
        }

        Member member = findMember(mId);
        Book book = searchBook(bId);

        IssueRecord record = new IssueRecord(mId, bId);

        book.decreaseAvailableCopies();
        member.incrementIssuedBookCount();

        issueRecords.add(record);
        return true;
    }

    // RETURN BOOK
    public boolean returnBook(String memberId, String bookId) {

        IssueRecord record = findActiveIssueRecord(memberId, bookId);
        if (record == null) {
            return false;
        }

        Book book = searchBook(bookId);
        Member member = findMember(memberId);

        record.markReturned();
        book.increaseAvailableCopies();
        member.decrementIssuedBookCount();

        return true;
    }
}
