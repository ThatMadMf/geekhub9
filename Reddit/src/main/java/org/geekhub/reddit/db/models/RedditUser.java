package org.geekhub.reddit.db.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class RedditUser {


    @Id
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
