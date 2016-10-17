package com.sep.repositories;

import com.sep.domain.EPRStatus;
import com.sep.domain.EventPlanningRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPlanningRequestRepository extends JpaRepository<EventPlanningRequest, Long> {
    List<EventPlanningRequest> findByStatus(EPRStatus status);
}