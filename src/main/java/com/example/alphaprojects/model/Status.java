package com.example.alphaprojects.model;
public enum Status {
    NOT_STARTED("Ikke startet"),
    IN_PROGRESS("Igangværende"),
    DONE("Afsluttet"),
    ARCHIVED("Arkiveret");

    private final String displayName;
    Status(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
