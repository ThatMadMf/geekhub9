package org.geekhub.reddit.db.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubredditDto {

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String creatorLogin;

    public SubredditDto() {
    }

    public SubredditDto(@NotEmpty @NotNull String name, @NotEmpty @NotNull String creatorLogin) {
        this.name = name;
        this.creatorLogin = creatorLogin;
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
}
