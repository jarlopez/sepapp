package com.sep.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="eventPlanningRequest")
public class EventPlanningRequest extends Auditable {
    @ManyToOne
    private Client client;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    private EPRStatus status = EPRStatus.NEW;
    // TODO Populate

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    private User creator;

    private Double budget;
    private Integer numAttendees;

    private Date fromDate;
    private Date toDate;

    @Lob
    private String financialFeedback;

    private String name;
    private String description;
    private String eventType;

    @ElementCollection
    private Set<EPRPreference> preferences = new HashSet<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EPRStatus getStatus() {
        return status;
    }

    public void setStatus(EPRStatus status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getNumAttendees() {
        return numAttendees;
    }

    public void setNumAttendees(Integer numAttendees) {
        this.numAttendees = numAttendees;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getFinancialFeedback() {
        return financialFeedback;
    }

    public void setFinancialFeedback(String financialFeedback) {
        this.financialFeedback = financialFeedback;
    }

    public Set<EPRPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<EPRPreference> preferences) {
        this.preferences = preferences;
    }
}
