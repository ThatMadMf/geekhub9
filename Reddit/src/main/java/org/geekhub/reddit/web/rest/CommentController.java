package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.dtos.VoteDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final PostService postService;

    public CommentController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Comment> getPostComments(@PathVariable("postId") int id) {
        return postService.getAllCommentsByPostId(id);
    }

    @GetMapping("{id}/votes")
    public List<Vote> getPostCommentVotes(@PathVariable("id") int id) {
        return postService.getAllVotesByCommentId(id);
    }

    @GetMapping("{id}/votes-count")
    public int getCommentVotesCount(@PathVariable("id") int id) {
        return postService.getAllVotesByCommentId(id).size();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "{id}/votes")
    public Vote addVoteToComment(@PathVariable("id") int id, @RequestBody VoteDto voteDto) {
        return postService.voteComment(new Vote(voteDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment addCommentToPost(@PathVariable("postId") int id, @RequestBody CommentDto commentDto) {
        return postService.addComment(new Comment(commentDto));
    }
}
