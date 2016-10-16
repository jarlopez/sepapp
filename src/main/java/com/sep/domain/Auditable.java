package com.sep.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class Auditable {
    @OneToMany
    private List<AuditRecord> auditHistory = new ArrayList<AuditRecord>();


    public List<AuditRecord> getAuditHistory() {
        return auditHistory;
    }

    public void setAuditHistory(List<AuditRecord> auditHistory) {
        this.auditHistory = auditHistory;
    }

    public void addToAuditHistory(AuditRecord ar) {
        this.auditHistory.add(ar);
    }
}