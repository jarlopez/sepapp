package com.sep.domain;

public enum FinanceRequestStatus {
    OPEN("Open"),
    PENDING("Client Review"),
    CLOSED("Closed"),
    REJECTED("Rejected");
    private String displayName;
    private FinanceRequestStatus(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }
}
