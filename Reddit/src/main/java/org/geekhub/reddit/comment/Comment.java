package org.geekhub.reddit.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Comment {

    private int id;

    @NotEmpty
    @NotNull
    private int creatorId;

    @NotEmpty
    @NotNull
    private int postId;

    @NotEmpty
    @NotNull
    private LocalDate creationDate;

    private String content;

    public Comment() {
    }

    public Comment(String content, int creatorId, int postId) {
        this.creatorId = creatorId;
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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
