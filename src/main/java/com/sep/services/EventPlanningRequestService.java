package com.sep.services;


import com.sep.domain.EventPlanningRequest;

public interface EventPlanningRequestService {
    Iterable<EventPlanningRequest> listAllEventPlanningRequests();

    EventPlanningRequest getEventPlanningRequestById(Long id);

    EventPlanningRequest saveEventPlanningRequest(EventPlanningRequest it);
}
