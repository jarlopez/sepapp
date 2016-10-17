package com.sep.repositories;

import com.sep.domain.FinanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRequestRepository extends JpaRepository<FinanceRequest, Long> {
}
