package org.geekhub.reddit.db.models;

import org.geekhub.reddit.dtos.PostDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {

    private int id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private int creatorId;

    @NotNull
    @NotEmpty
    private int subredditId;

    private String content;

    @NotNull
    @NotEmpty
    private LocalDate creationDate;

    private List<Comment> comments;


    public Post() {
        comments = new ArrayList<>();
    }

    public Post(PostDto postDto, int creatorId, int subredditId) {
        this.title = postDto.getTitle();
        this.creatorId = creatorId;
        this.subredditId = subredditId;
        this.content = postDto.getContent();
        creationDate = LocalDate.now();
        comments = new ArrayList<>();
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
