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
    private boolean vote;

    @NotNull
    @NotEmpty
    private int appliedId;

    private VoteApplicable voteApplicable;

    private LocalDate voteDate;

    public Vote() {

    }

    public Vote(VoteDto voteDto, int appliedId) {
        voteApplicable = voteDto.getApplicable();
        voterLogin = voteDto.getVoterLogin();
        vote = voteDto.isVote();
        voteDate = LocalDate.now();
        this.appliedId = appliedId;
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

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    public int getAppliedId() {
        return appliedId;
    }

    public void setAppliedId(int appliedId) {
        this.appliedId = appliedId;
    }

    public VoteApplicable getVoteApplicable() {
        return voteApplicable;
    }

    public void setVoteApplicable(String voteApplicable) {
        this.voteApplicable = VoteApplicable.valueOf(voteApplicable);
    }
}
