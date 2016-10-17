package com.sep.domain;

import javax.persistence.*;

/**
 * Created by Default Cute on 16-10-2016.
 */
@Entity
@Table(name="FinancialRequest")
public class FinancialRequest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    private User sender;
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}


