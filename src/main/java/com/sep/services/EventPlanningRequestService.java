package com.sep.services;


import com.sep.domain.EPRStatus;
import com.sep.domain.EventPlanningRequest;

import java.util.List;

public interface EventPlanningRequestService {
    Iterable<EventPlanningRequest> listAllEventPlanningRequests();
    EventPlanningRequest getEventPlanningRequestById(Long id);
    EventPlanningRequest saveEventPlanningRequest(EventPlanningRequest it);
    List<EventPlanningRequest> getEventPlanningRequestsByStatus(EPRStatus status);
}
