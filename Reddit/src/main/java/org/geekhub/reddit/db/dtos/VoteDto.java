package org.geekhub.reddit.db.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VoteDto {

    @NotNull
    @NotEmpty
    private String voterLogin;

    @NotEmpty
    @NotNull
    private boolean vote;

    private int postId;

    private int commentId;
}
