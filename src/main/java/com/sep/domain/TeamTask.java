package com.sep.domain;

import javax.persistence.*;

@Entity
public class TeamTask extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    private User sender;
// TODO STATUS
    @OneToOne(targetEntity = User.class)
    private User assignedTo;

    @ManyToOne
    private EventPlanningRequest epr;

    @Lob
    private String description;

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
}
