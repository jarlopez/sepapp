package com.sep.domain;

public enum Department {
    ADMINISTRATION("Administration"),
    PRODUCTION("Production"),
    SERVICES("Services"),
    FINANCIAL("Financial");

    private String displayName;
    private Department(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }
}
