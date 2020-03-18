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

    private Integer postId;

    private Integer commentId;

    public String getVoterLogin() {
        return voterLogin;
    }

    public void setVoterLogin(String voterLogin) {
        this.voterLogin = voterLogin;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}
