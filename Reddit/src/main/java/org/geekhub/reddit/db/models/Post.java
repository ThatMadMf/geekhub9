package org.geekhub.reddit.db.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.time.LocalDate;

public class Post {

    @Id
    private int id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String creatorLogin;

    @NotNull
    @NotEmpty
    private int subredditId;

    private String content;

    @NotNull
    @NotEmpty
    private LocalDate creationDate;

    public Post() {
    }

    public Post(PostDto postDto) {
        this.title = postDto.getTitle();
        this.creatorLogin = postDto.getCreatorLogin();
        this.subredditId = postDto.getSubredditId();
        this.content = postDto.getContent();
        creationDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public int getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(int subredditId) {
        this.subredditId = subredditId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
