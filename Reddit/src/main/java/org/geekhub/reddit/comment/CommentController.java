package org.geekhub.reddit.comment;

import org.geekhub.reddit.vote.Vote;
import org.geekhub.reddit.vote.VoteDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/p/{postId}/comments")
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
        return commentService.getVotesCount(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "{id}/votes")
    public Vote addVoteToComment(@PathVariable("id") int id, @AuthenticationPrincipal Principal principal,
                                 @RequestBody VoteDto voteDto) {
        return commentService.voteComment(voteDto, principal.getName(), id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment addCommentToPost(@PathVariable("postId") int id, @RequestBody String content,
                                    @AuthenticationPrincipal Principal principal) {
        return commentService.addComment(content, principal.getName(), id);
    }
}
