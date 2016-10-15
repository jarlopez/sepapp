package com.sep.common;

public enum UserRole {
    // Internal & Testing
    ADMIN,

    // Administrative Department
    ADMIN_MANAGER,

    CUSTOMER_SERVICE,
    CUSTOMER_SERVICE_MANAGER,

    HR_MEMBER,
    HR_MANAGER,

    MARKETING_MEMBER,
    MARKETING_MANAGER,

    // Financial Department
    FINANCIAL_MANAGER,
    ACCOUNTANT,

    // Production & Services
    TEAM_MEMBER,
    TEAM_MANAGER,

    // Upper Management
    VICE_PRESIDENT;

    public String roleName() {
        return "ROLE_" + this.name();
    }
}
