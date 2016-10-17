package com.sep.repositories;

import com.sep.domain.EventPlanningRequest;
import com.sep.domain.FinancialRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Default Cute on 16-10-2016.
 */
@Repository
public interface FinancialRequestRepository extends JpaRepository<EventPlanningRequest, Long> {
    void save(FinancialRequest maximizeBudget);
}
