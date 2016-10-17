package com.sep.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TeamTask extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    private User sender;

    @OneToOne(targetEntity = User.class)
    private User assignedTo;

    @ManyToOne
    @NotNull
    private EventPlanningRequest epr;

    private TaskStatus status = TaskStatus.OPEN;

    @Lob
    private String description;

    @Lob
    private String feedback;

    private TaskPriority priority;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public EventPlanningRequest getEpr() {
        return epr;
    }

    public void setEpr(EventPlanningRequest epr) {
        this.epr = epr;
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

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
