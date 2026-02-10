package com.alrms.service;

import com.alrms.model.Book;
import com.alrms.model.Member;
import java.util.ArrayList;

public class LibraryManager {

    private ArrayList<Book> books = new ArrayList<>();

    // Add new book
    public boolean addBook(Book book) {

        // prevent duplicate book ID
        for (Book b : books) {
            if(b.getBookId().equalsIgnoreCase(book.getBookId())) {
                return false;
            }
        }

        books.add(book);
        return true;
    }

    // Search book by ID
    public Book searchBook(String bookId) {
        for (Book b : books) {
            if(b.getBookId().equalsIgnoreCase(bookId)) {
                return b;
            }
        }
        return null;
    }

    // Check availability
    public boolean isBookAvailable(String bookId) {
        Book book = searchBook(bookId);
        return book != null && book.getAvailableCopies() > 0;
    }

    private ArrayList<Member> members = new ArrayList<>();

    // Add Member
    public boolean addMember(Member member) {
        if(member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if(findMember(member.getMemberID()) != null) {
            return false;
        }
        members.add(member);
        return true;
    }


    // Core search method
    public Member findMember(String memberId) {
        if(memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }

        String trimmedId = memberId.trim();

        for(Member m : members) {
            if (m.getMemberID().equalsIgnoreCase(trimmedId)) {
                return m;
            }
        }
        return null;
    }

    // Helper boolean method (optional but very useful)
    public boolean memberExists(String memberId) {
        return findMember(memberId) != null;
    }

    private static final int MAX_ISSUE_LIMIT = 3;

    public boolean canMemberIssue(String memberId) {

        Member member = findMember(memberId);

        if (member == null) {
            return false; // member does not exist
        }

        return member.getIssuedBookCount() < MAX_ISSUE_LIMIT;
    }
}
