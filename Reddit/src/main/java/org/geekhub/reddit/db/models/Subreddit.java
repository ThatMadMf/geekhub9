package org.geekhub.reddit.db.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class Subreddit {

    @Id
    private int id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String creatorLogin;

    @NotNull
    @NotEmpty
    private LocalDate creationDate;

    private List<Post> posts;

    public Subreddit() {
    }

    public Subreddit(@NotNull @NotEmpty String name, @NotNull @NotEmpty String creatorLogin,
                     @NotNull @NotEmpty LocalDate creationDate) {
        this.name = name;
        this.creatorLogin = creatorLogin;
        this.creationDate = creationDate;
    }

    public Subreddit(int id, @NotNull @NotEmpty String name, @NotNull @NotEmpty String creatorLogin,
                     @NotNull @NotEmpty LocalDate creationDate) {
        this(name, creatorLogin, creationDate);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
