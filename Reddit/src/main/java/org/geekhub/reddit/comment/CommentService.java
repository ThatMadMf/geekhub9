package org.geekhub.reddit.comment;

import org.geekhub.reddit.exception.NoRightsException;
import org.geekhub.reddit.user.UserRepository;
import org.geekhub.reddit.vote.CommentVote;
import org.geekhub.reddit.vote.Vote;
import org.geekhub.reddit.vote.VoteApplicable;
import org.geekhub.reddit.vote.VoteService;
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

    public Comment getCommentById(int id) {
        return commentRepository.getCommentById(id);
    }

    public List<Vote> getAllVotesByCommentId(int id) {
        return voteService.getAllVotesByAppliedId(id, VoteApplicable.COMMENT);
    }

    public List<Comment> getAllCommentsByPostId(int postId) {
        return commentRepository.getCommentsByPostId(postId);
    }

    public Comment addComment(String content, String authorName, int id) {
        Comment comment = new Comment(content, userRepository.getUserByLogin(authorName).getId(), id);

        return commentRepository.createComment(comment);
    }

    public Vote voteComment(boolean vote, String authorLogin, int id) {
        Vote newVote = new CommentVote(vote, userRepository.getUserByLogin(authorLogin).getId(), id);
        return voteService.submitVote(newVote);
    }

    public int getVotesCount(int id) {
        return getAllVotesByCommentId(id).stream()
                .mapToInt(vote -> vote.isVote() ? 1 : -1).sum();
    }

    public Comment editComment(String content, String name, int id) {
        Comment comment = getCommentById(id);
        checkAuthority(comment, name);

        return commentRepository.editComment(content, id);
    }

    private void checkAuthority(Comment comment, String name) {
        if (comment.getCreatorId() != userRepository.getUserByLogin(name).getId()) {
            throw new NoRightsException("You have no rights to edit this post");
        }
    }

    public void deleteComment(String name, int id) {
        Comment comment = getCommentById(id);
        checkAuthority(comment, name);

        commentRepository.deleteComment(id);
    }
}
