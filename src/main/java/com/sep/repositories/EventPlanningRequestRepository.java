package com.sep.repositories;

import com.sep.domain.EventPlanningRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPlanningRequestRepository extends JpaRepository<EventPlanningRequest, Long>{
}