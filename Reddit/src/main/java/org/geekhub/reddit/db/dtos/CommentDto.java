package org.geekhub.reddit.db.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommentDto {

    @NotEmpty
    @NotNull
    private String creatorLogin;

    @NotEmpty
    @NotNull
    private int postId;

    private String content;

    public CommentDto() {
        
    }

    public CommentDto(@NotEmpty @NotNull String creatorLogin, @NotEmpty @NotNull int postId, String content) {
        this.creatorLogin = creatorLogin;
        this.postId = postId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
