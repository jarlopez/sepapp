package com.sep.repositories;

import com.sep.domain.AuditRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<AuditRecord, Long> {
}