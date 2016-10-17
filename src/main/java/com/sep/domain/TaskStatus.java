package com.sep.domain;

public enum TaskStatus {
    OPEN("Open"),
    FEEDBACK_SUBMITTED("Feedback submitted"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    VERIFIED("Verified by manager");

    private String displayName;
    private TaskStatus(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return displayName;
    }
}
