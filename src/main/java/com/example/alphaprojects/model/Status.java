package com.example.alphaprojects.model;
public enum Status {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
    ARCHIVED("Archived");

    private final String displayName;
    Status(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
