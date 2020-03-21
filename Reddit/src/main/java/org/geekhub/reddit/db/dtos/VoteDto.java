package org.geekhub.reddit.db.dtos;

import org.geekhub.reddit.db.models.VoteApplicable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VoteDto {

    @NotNull
    @NotEmpty
    private String voterLogin;

    @NotEmpty
    @NotNull
    private boolean vote;

    private VoteApplicable applicable;

    public VoteDto() {
    }

    public VoteDto(@NotNull @NotEmpty String voterLogin, @NotEmpty @NotNull boolean vote, VoteApplicable applicable) {
        this.voterLogin = voterLogin;
        this.vote = vote;
        this.applicable = applicable;
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

    public VoteApplicable getApplicable() {
        return applicable;
    }

    public void setApplicable(VoteApplicable applicable) {
        this.applicable = applicable;
    }
}
