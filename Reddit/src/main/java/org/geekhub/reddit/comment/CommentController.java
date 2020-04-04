package org.geekhub.reddit.comment;

import org.geekhub.reddit.vote.Vote;
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
                                 @RequestBody boolean vote) {
        return commentService.voteComment(vote, principal.getName(), id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment addCommentToPost(@PathVariable("postId") int id, @RequestBody String content,
                                    @AuthenticationPrincipal Principal principal) {
        return commentService.addComment(content, principal.getName(), id);
    }

    @PutMapping("{id}")
    public Comment editComment(@PathVariable("id") int id, @RequestBody String content,
                               @AuthenticationPrincipal Principal principal) {
        return commentService.editComment(content, principal.getName(), id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable("id") int id, @AuthenticationPrincipal Principal principal) {
        commentService.deleteComment(principal.getName(), id);
    }
}
