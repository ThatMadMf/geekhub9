package org.geekhub.reddit.db.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Vote {

    @Id
    private int id;

    @NotNull
    @NotEmpty
    private String voterLogin;

    @NotNull
    @NotEmpty
    private boolean vote;

    private int postId;

    private int commentId;

    private LocalDate vote_date;

    public Vote() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public LocalDate getVote_date() {
        return vote_date;
    }

    public void setVote_date(LocalDate vote_date) {
        this.vote_date = vote_date;
    }
}
