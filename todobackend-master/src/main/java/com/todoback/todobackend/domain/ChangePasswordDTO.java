package com.todoback.todobackend.domain;



public class ChangePasswordDTO {
    String oldPassword;
    String newPassword;
    String newPasswordR;
    String username;

    User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordR() {
        return newPasswordR;
    }

    public void setNewPasswordR(String newPasswordR) {
        this.newPasswordR = newPasswordR;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
