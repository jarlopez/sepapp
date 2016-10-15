package com.sep.domain;

public enum EPRStatus {
    NEW("New"),
    REVIEWED_BY_CS("Approved (customer service)"),
    REVIEWED_BY_FINANCE("Reviewed (finance)"),
    APPROVED("Approved (admin. manager)"),
    OPEN("Open"),
    IN_PROGRESS("In Progress)"),
    REJECTED("Rejected"),
    REJECTED_CS("Rejected by customer service manager"),
    REJECTED_ADMIN("Rejected by administration manager"),
    CLOSED("Closed"),
    ARCHIVED("Archived");

    private String displayName;
    private EPRStatus(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }

}
