package com.sep.services;

import com.sep.domain.AuditRecord;
import com.sep.domain.Auditable;
import com.sep.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public class AuditServiceImpl implements AuditService {
    @Autowired ApplicationContext ctx;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public Iterable<AuditRecord> listAllAuditRecords() {
        return auditRepository.findAll();
    }

    @Override
    public AuditRecord getAuditRecordById(Long id) {
        return auditRepository.findOne(id);
    }

    @Override
    public AuditRecord saveAuditRecord(AuditRecord it) {
        return auditRepository.save(it);
    }

    @Override
    public Iterable<AuditRecord> getHistoryForRepoAndId(CrudRepository repository, Long id) {
        Auditable it = (Auditable) repository.findOne(id);
        return it.getAuditHistory();
    }
}
