package com.sep.services;

import com.sep.domain.StaffRequest;

/**
 * Created by Default Cute on 17-10-2016.
 */
public interface StaffRequestService {
    void save(StaffRequest Department);

    StaffRequest findByDepartment(String Department);
}
