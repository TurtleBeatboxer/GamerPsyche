package com.todoback.todobackend.domain;

import com.merakianalytics.orianna.types.common.Region;

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
    private LOLServer lolServer;
    private String passwordChangeId;
    private String riotUserPuuId;
    private Region lolRegion;
    private boolean activated;

    public Region getLolRegion() {
        return lolRegion;
    }

    public void setLolRegion(Region lolRegion) {
        this.lolRegion = lolRegion;
    }




    public String getLolUsername() {
        return lolUsername;
    }

    public LOLServer getLolServer() {
        return lolServer;
    }

    public void setLolServer(LOLServer lolServer) {
        this.lolServer = lolServer;
    }

    public String getRiotUserPuuId() {
        return riotUserPuuId;
    }

    public void setRiotUserPuuId(String riotUserPuuId) {
        this.riotUserPuuId = riotUserPuuId;
    }

    public User() {

    }

    public String getPasswordChangeId() {
        return passwordChangeId;
    }

    public void setPasswordChangeId(String passwordChangeId){
        this.passwordChangeId = passwordChangeId;
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

    public LOLServer getLOLServer() {
        return lolServer;
    }

    public void setLOLServer(LOLServer lolServer) {
        this.lolServer = lolServer;
    }
}
