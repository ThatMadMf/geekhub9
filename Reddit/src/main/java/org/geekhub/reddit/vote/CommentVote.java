package org.geekhub.reddit.vote;

public class CommentVote extends Vote {

    public CommentVote() {
    }

    public CommentVote(boolean vote, int voterId, int appliedId) {
        super(vote, voterId, appliedId);
    }

    @Override
    public VoteApplicable getVoteApplicable() {
        return VoteApplicable.COMMENT;
    }
}
