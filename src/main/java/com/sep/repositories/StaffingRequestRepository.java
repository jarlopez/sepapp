package com.sep.repositories;

import com.sep.domain.StaffingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffingRequestRepository extends JpaRepository<StaffingRequest, Long> {
//    List<StaffingRequest> findByContractType(ContractType type);
}