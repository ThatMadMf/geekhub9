package org.geekhub.reddit.vote;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAllVotesByPostId(int postId) {
        return voteRepository.getVotesByPostId(postId);
    }

    public List<Vote> getAllVotesByCommentId(int commentId) {
        return voteRepository.getVotesByCommentId(commentId);
    }

    public Vote submitVote(Vote vote) {
        if (voteExists(vote)) {
            throw new DataBaseRowException("Vote already exists");
        }
        return voteRepository.submitVote(vote);
    }

    private boolean voteExists(Vote vote) {
        return voteRepository.voteExists(vote);
    }

    public void deleteVote(Vote vote) {
        if (!voteExists(vote)) {
            throw new DataBaseRowException("Vote is absent in database");
        }
        voteRepository.deleteVote(vote);
    }
}
