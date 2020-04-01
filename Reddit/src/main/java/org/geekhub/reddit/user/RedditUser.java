package org.geekhub.reddit.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RedditUser {


    private int id;

    @NotNull
    @NotEmpty
    private String login;

    private LocalDate registrationDate;

    public RedditUser() {
    }

    public RedditUser(@NotNull @NotEmpty String login, LocalDate date) {
        this.login = login;
        this.registrationDate = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
