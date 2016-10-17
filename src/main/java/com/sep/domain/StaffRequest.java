package com.sep.domain;

import javax.persistence.*;

/**
 * Created by Default Cute on 17-10-2016.
 */
@Entity
@Table(name = "StaffRequest")
public class StaffRequest {

    private Long id;
    private String RequiredStaff;
    private String Department;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequiredStaff() {
        return RequiredStaff;
    }

    public void setRequiredStaff(String RequiredStaff) {
        this.RequiredStaff = RequiredStaff;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }
}
