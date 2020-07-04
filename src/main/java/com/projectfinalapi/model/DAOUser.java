package com.projectfinalapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USERS")
public class DAOUser {

    @Id
    @Column(name = "USER_ID") 
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id auto increment
    private long id;
    
    @Column(name = "USERNAME") 
    private String username;
    
    @Column(name = "PASSWORD") 
    private String password;
    
    @Column(name = "ROLE") 
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

