package com.sep.domain;

public enum ContractType {
    FULL_TIME("Full time"),
    PART_TIME("Part time");

    private String displayName;
    private ContractType(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }
}
