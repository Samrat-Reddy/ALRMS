package com.alrms.model;

public class Member {

    private String memberID;
    private String memberName;
    private int issuedBookCount;

    public Member(String memberID, String memberName) {
        setMemberID(memberID);
        setMemberName(memberName);
    }

    public void setMemberID(String memberID) {
        if (memberID == null) {
            throw new IllegalArgumentException("Member ID cannot be null");
        }

        String trimmedID = memberID.trim();
        if (trimmedID.isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be empty or blank");
        }

        this.memberID = trimmedID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberName(String memberName) {
        if (memberName == null) {
            throw new IllegalArgumentException("Member name cannot be null");
        }

        String trimmedName = memberName.trim();
        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty or blank");
        }

        this.memberName = trimmedName;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getIssuedBookCount() {
        return issuedBookCount;
    }

    public void incrementIssuedBookCount() {
        issuedBookCount++;
    }

    public void decrementIssuedBookCount() {
        if (issuedBookCount == 0) {
            throw new IllegalStateException("Issued book count cannot be negative");
        }
        issuedBookCount--;
    }
}
