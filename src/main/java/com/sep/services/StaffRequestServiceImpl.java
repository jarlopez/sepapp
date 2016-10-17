package com.sep.services;

import com.sep.domain.StaffRequest;
import com.sep.repositories.StaffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Default Cute on 17-10-2016.
 */
@Service
public class StaffRequestServiceImpl implements StaffRequestService{
    @Autowired
    private StaffRequestRepository staffRequestRepository;

    @Override
    public void save(StaffRequest Department) {
        staffRequestRepository.save(Department);
    }

    @Override
    public StaffRequest findByDepartment(String Department) {
        return staffRequestRepository.findByDepartment();
    }

}
