package net.milgar.sharedoc.domain.web;

import net.milgar.sharedoc.domain.model.User;

public class RegistrationRequest {

    private User user;
    private String role;

    public RegistrationRequest() {
        this.user = new User();
        this.role = "Student";
    }

    public RegistrationRequest(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
