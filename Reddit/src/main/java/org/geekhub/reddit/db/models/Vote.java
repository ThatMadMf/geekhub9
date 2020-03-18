package org.geekhub.reddit.db.models;

import org.geekhub.reddit.db.dtos.VoteDto;
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
    private boolean upvote;

    private Integer postId;

    private Integer commentId;

    private LocalDate voteDate;

    public Vote() {

    }

    public Vote(VoteDto voteDto) {
        voterLogin = voteDto.getVoterLogin();
        upvote = voteDto.isVote();
        postId = voteDto.getPostId();
        commentId = voteDto.getCommentId();
        voteDate = LocalDate.now();
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

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
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

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }
}
