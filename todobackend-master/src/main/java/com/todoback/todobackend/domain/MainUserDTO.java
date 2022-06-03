package com.todoback.todobackend.domain;

public class MainUserDTO {

    private String lolUsername;
    private String username;
    private LOLServer lolServer;
    private String email;
    private String firstName;
    private String lastName;

 
    public void setUsername(String username){
        this.username = usernam;
    }
    public String getUsername(){
        return username;
    }
    public String getLolUsername() {
        return lolUsername;
    }

    public void setLolUsername(String lolUsername) {
        this.lolUsername = lolUsername;
    }

    public LOLServer getLolServer() {
        return lolServer;
    }

    public void setLolServer(LOLServer lolServer) {
        this.lolServer = lolServer;
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





}
