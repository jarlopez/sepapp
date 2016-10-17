package com.sep.repositories;

import com.sep.domain.StaffRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Default Cute on 17-10-2016.
 */
public interface StaffRequestRepository extends JpaRepository<StaffRequest, Long> {
    StaffRequest findByDepartment();
}
