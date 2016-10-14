package com.sep.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany
    @PrimaryKeyJoinColumn
    private Set<EventPlanningRequest> eprs;

    public Long getId() {
        return id;
    }

    public Set<EventPlanningRequest> getEprs() {
        return eprs;
    }

    public void setEprs(Set<EventPlanningRequest> eprs) {
        this.eprs = eprs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//    private Long id;
//    private String username;
//
//    @Lob
//    @Column
//    private String password;
//    private String passwordConfirm;
//    private Set<Role> roles;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Transient
//    public String getPasswordConfirm() {
//        return passwordConfirm;
//    }
//
//    public void setPasswordConfirm(String passwordConfirm) {
//        this.passwordConfirm = passwordConfirm;
//    }
//
//    @ManyToMany
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
}