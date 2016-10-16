package com.sep.domain;

public enum TaskPriority {
    LOWEST("Lowest"),
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    HIGHEST("Highest");

    private String displayName;
    private TaskPriority(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }
}
