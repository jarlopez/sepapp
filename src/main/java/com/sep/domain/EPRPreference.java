package com.sep.domain;

public enum EPRPreference {
    DECORATIONS("Decorations"),
    PARTIES("Parties"),
    MEDIA("Photos/filming"),
    FOOD("Breakfast, lunch, dinner"),
    DRINK("Soft/hot drinks");

    private String displayName;
    private EPRPreference(String name) {
        this.displayName = name;
    }
    public String getDisplayName() {
        return this.displayName;
    }

}
