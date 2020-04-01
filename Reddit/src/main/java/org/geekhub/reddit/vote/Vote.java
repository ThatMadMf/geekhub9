package org.geekhub.reddit.vote;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Vote {

    private int id;

    @NotNull
    @NotEmpty
    private int voterId;

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

    public Vote(VoteDto voteDto, int voterId, int appliedId) {
        this.voterId = voterId;
        this.appliedId = appliedId;
        this.voteApplicable = voteDto.getApplicable();
        this.vote = voteDto.isVote();
        voteDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
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
