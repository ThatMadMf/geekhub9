package org.geekhub.crypto.web.model;

public class User {
    private String username;
    private String password;
    private String[] roles;
    private int passwordUses;

    public User(String username, String password, int passwordUses, String... roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.passwordUses = passwordUses;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String[] getRoles() {
        return roles;
    }

    public int getPasswordUses() {
        return passwordUses;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordUses(int passwordUses) {
        this.passwordUses = passwordUses;
    }

    public void incrementPasswordUse() {
        this.passwordUses++;
    }
}
