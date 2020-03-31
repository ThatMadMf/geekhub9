package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.db.repositories.CommentRepository;
import org.geekhub.reddit.db.repositories.UserRepository;
import org.geekhub.reddit.dtos.VoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final VoteService voteService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(VoteService voteService, UserRepository userRepository, CommentRepository commentRepository) {
        this.voteService = voteService;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<Vote> getAllVotesByCommentId(int id) {
        return voteService.getAllVotesByCommentId(id);
    }

    public List<Comment> getAllCommentsByPostId(int postId) {
        return commentRepository.getCommentsByPostId(postId);
    }

    public Comment addComment(String content, String authorName, int id) {
        Comment comment = new Comment(content, userRepository.getUserByLogin(authorName).getId(), id);

        return commentRepository.createComment(comment);
    }

    public Vote voteComment(VoteDto voteDto, String authorLogin, int id) {
        Vote vote = new Vote(voteDto, userRepository.getUserByLogin(authorLogin).getId(), id);
        return voteService.submitVote(vote);
    }

    public int getVotesCount(int id) {
        return getAllVotesByCommentId(id).stream()
                .mapToInt(vote -> vote.isVote() ? 1 : -1).sum();
    }
}
