package org.geekhub.reddit.db.dtos;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostDto {

    @NotNull
    @NotEmpty
    private String creatorLogin;

    @NotNull
    @NotEmpty
    private int subredditId;

    @NotNull
    @NotEmpty
    private  String title;

    private String content;

    public PostDto() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
