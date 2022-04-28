package com.todoback.todobackend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String activationId;
    private String codeNumber;
    private String lolUsername;
<<<<<<< HEAD
    private LOLData.LOLServer lolServer;
=======
    private LOLServer lolServer;
>>>>>>> GP-65FIX
    private boolean activated;


    public User() {

    }

    public String getActivationId() {
        return activationId;
    }

    public void setActivationId(String activationId) {
        this.activationId = activationId;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLOLUsername() {
        return lolUsername;
    }

    public void setLolUsername(String lolUsername) {
        this.lolUsername = lolUsername;
    }

<<<<<<< HEAD
    public LOLData.LOLServer getLOLServer() {
        return lolServer;
    }

    public void setLOLServer(LOLData.LOLServer lolServer) {
=======
    public LOLServer getLOLServer() {
        return lolServer;
    }

    public void setLOLServer(LOLServer lolServer) {
>>>>>>> GP-65FIX
        this.lolServer = lolServer;
    }

}
