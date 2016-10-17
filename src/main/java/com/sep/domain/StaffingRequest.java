package com.sep.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class StaffingRequest {
    @NotNull
    private ContractType contractType;
    @NotNull
    private Department department;
    private Integer yearsOfXp = 0;

    private String jobTitle;
    private String description;

    private StaffingRequestStatus status = StaffingRequestStatus.OPEN;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getYearsOfXp() {
        return yearsOfXp;
    }

    public void setYearsOfXp(Integer yearsOfXp) {
        this.yearsOfXp = yearsOfXp;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaffingRequestStatus getStatus() {
        return status;
    }

    public void setStatus(StaffingRequestStatus status) {
        this.status = status;
    }
}
