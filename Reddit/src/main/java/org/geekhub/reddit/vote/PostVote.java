package org.geekhub.reddit.vote;

public class PostVote extends Vote {

    public PostVote() {
    }

    public PostVote(boolean vote, int voterId, int appliedId) {
        super(vote, voterId, appliedId);
    }

    @Override
    public VoteApplicable getVoteApplicable() {
        return VoteApplicable.POST;
    }
}
