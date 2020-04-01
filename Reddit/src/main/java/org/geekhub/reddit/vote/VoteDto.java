package org.geekhub.reddit.vote;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VoteDto {

    @NotEmpty
    @NotNull
    private boolean vote;

    private VoteApplicable applicable;

    public VoteDto() {
    }

    public VoteDto( @NotEmpty @NotNull boolean vote, VoteApplicable applicable) {
        this.vote = vote;
        this.applicable = applicable;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public VoteApplicable getApplicable() {
        return applicable;
    }

    public void setApplicable(VoteApplicable applicable) {
        this.applicable = applicable;
    }
}
