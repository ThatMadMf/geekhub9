package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.dtos.VoteDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.services.CommentService;
import org.geekhub.reddit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getPostComments(@PathVariable("postId") int id) {
        return commentService.getAllCommentsByPostId(id);
    }

    @GetMapping("{id}/votes")
    public List<Vote> getPostCommentVotes(@PathVariable("id") int id) {
        return commentService.getAllVotesByCommentId(id);
    }

    @GetMapping("{id}/votes-count")
    public int getCommentVotesCount(@PathVariable("id") int id) {
        return commentService.getAllVotesByCommentId(id).stream()
                .mapToInt(vote -> vote.isVote() ? 1 : -1 ).sum();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "{id}/votes")
    public Vote addVoteToComment(@PathVariable("id") int id, @RequestBody VoteDto voteDto) {
        return commentService.voteComment(new Vote(voteDto, id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment addCommentToPost(@PathVariable("postId") int id, @RequestBody CommentDto commentDto) {
        return commentService.addComment(new Comment(commentDto));
    }
}