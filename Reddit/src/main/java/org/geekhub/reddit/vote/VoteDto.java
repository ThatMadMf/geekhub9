package org.geekhub.reddit.vote;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VoteDto {

    @NotEmpty
    @NotNull
    private boolean vote;

    private int appliedId;

    public VoteDto() {
    }

    public VoteDto(@NotEmpty @NotNull boolean vote, int appliedId) {
        this.vote = vote;
        this.appliedId = appliedId;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public int getAppliedId() {
        return appliedId;
    }

    public void setAppliedId(int appliedId) {
        this.appliedId = appliedId;
    }
}
