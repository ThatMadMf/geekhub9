package org.geekhub.reddit.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PrivateRedditUser extends RedditUser {


    private String role;

    private String email;

    private String password;

    public PrivateRedditUser() {
    }

    public PrivateRedditUser(@NotNull @NotEmpty String login, LocalDate date, String role, String email) {
        super(login, date);
        this.role = role;
        this.email = email;
    }

    public PrivateRedditUser(@NotNull @NotEmpty String login, LocalDate date, String role,
                             String email, String password) {
        super(login, date);
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
