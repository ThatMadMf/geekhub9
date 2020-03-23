package org.geekhub.reddit.db.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Comment {

    @Id
    private int id;

    @NotEmpty
    @NotNull
    private String creatorLogin;

    @NotEmpty
    @NotNull
    private int postId;

    @NotEmpty
    @NotNull
    private LocalDate creationDate;

    private String content;

    public Comment() {
    }

    public Comment(String content, String creatorLogin, int postId) {
        this.creatorLogin = creatorLogin;
        this.postId = postId;
        this.content = content;
        this.creationDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
