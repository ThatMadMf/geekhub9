package org.geekhub.reddit.vote;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAllVotesByAppliedId(int appliedId, VoteApplicable voteApplicable) {
        return voteRepository.getVotesByAppliedId(appliedId, voteApplicable);
    }

    public Vote submitVote(Vote vote) {
        return voteRepository.submitVote(vote);
    }
}
