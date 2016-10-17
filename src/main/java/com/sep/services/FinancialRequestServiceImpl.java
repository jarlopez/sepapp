package com.sep.services;

import com.sep.domain.EventPlanningRequest;
import com.sep.domain.FinancialRequest;
import com.sep.repositories.FinancialRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FinancialRequestServiceImpl implements FinancialRequestService {

    @Autowired
    private FinancialRequestRepository financialRequestRepository;


    public FinancialRequest saveFinancialRequest(FinancialRequest MaximizeBudget) {
        financialRequestRepository.save(MaximizeBudget);
        return MaximizeBudget;
    }

    @Override
    public Iterable<EventPlanningRequest> listAllFinancialRequests() {
        return null;
    }

    @Override
    public FinancialRequest getEventPlanningRequestById(Long id) {
        return null;
    }
}