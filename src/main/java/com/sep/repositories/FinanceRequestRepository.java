package com.sep.repositories;

import com.sep.domain.FinanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Default Cute on 17-10-2016.
 */
@Repository
public interface FinanceRequestRepository extends JpaRepository<FinanceRequest, Long> {
}
