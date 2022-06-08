package com.todoback.todobackend.domain;

public class RegisterDTO {

    private LOLData lolData;
    private String email;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public LOLData getLolData() {
        return lolData;
    }

    public void setLolData(LOLData lolData) {
        this.lolData = lolData;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
