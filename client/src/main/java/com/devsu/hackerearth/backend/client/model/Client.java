package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Entity;

@Entity
public class Client extends Person {
    
    private Long id;
    private String password;
    private boolean isActive;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
}