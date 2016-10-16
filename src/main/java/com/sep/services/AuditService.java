package com.sep.services;

import com.sep.domain.AuditRecord;
import org.springframework.data.repository.CrudRepository;

public interface AuditService {
    Iterable<AuditRecord> listAllAuditRecords();
    AuditRecord getAuditRecordById(Long id);
    AuditRecord saveAuditRecord(AuditRecord it);
    Iterable<AuditRecord> getHistoryForRepoAndId(CrudRepository repository, Long id);
}
