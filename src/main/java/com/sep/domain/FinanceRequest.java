package com.sep.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FinanceRequest {


    private Department requestingDepartment;

    private String projectReference;
    private Integer requiredAmount = 10000;
    private String reason;

    private FinanceRequestStatus status = FinanceRequestStatus.OPEN;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Department getRequestingDepartment() {
        return requestingDepartment;
    }

    public void setRequestingDepartment(Department requestingDepartment) {
        this.requestingDepartment = requestingDepartment;
    }

    public String getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(String projectReference) {
        this.projectReference = projectReference;
    }

    public Integer getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(Integer requiredAmount) {
        this.requiredAmount = requiredAmount;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public FinanceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FinanceRequestStatus status) {
        this.status = status;
    }
}
