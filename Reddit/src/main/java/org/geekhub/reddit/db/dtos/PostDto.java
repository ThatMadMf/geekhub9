package org.geekhub.reddit.db.dtos;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostDto {

    @NotNull
    @NotEmpty
    private  String title;

    private String content;

    public PostDto() {
    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
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
