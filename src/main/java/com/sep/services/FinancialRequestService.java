package com.sep.services;


import com.sep.domain.EventPlanningRequest;
import com.sep.domain.FinancialRequest;

public interface FinancialRequestService {


    FinancialRequest getEventPlanningRequestById(Long id);

    Iterable<EventPlanningRequest> listAllFinancialRequests();
}