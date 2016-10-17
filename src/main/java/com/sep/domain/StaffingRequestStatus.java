package com.sep.domain;

public enum StaffingRequestStatus {
    OPEN("Open"),
    PENDING("In Review"),
    CLOSED("Closed"),
    REJECTED("Rejected");
    private String displayName;
    private StaffingRequestStatus(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }
}
