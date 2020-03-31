package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.dtos.PostDto;
import org.geekhub.reddit.dtos.VoteDto;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping(value = "api/r/{subredditId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ResponseBody
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPostBySubredditId(@PathVariable("subredditId") int subredditId) {
        return postService.getAllPostBySubredditId(subredditId);
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @GetMapping("{id}/votes")
    public List<Vote> getPostVotes(@PathVariable("id") int id) {
        return postService.getAllVotesByPostId(id);
    }

    @GetMapping("{id}/votes-count")
    public int getPostVotesCount(@PathVariable("id") int id) {
        return postService.getAllVotesByPostId(id).stream()
                .mapToInt(vote -> vote.isVote() ? 1 : -1).sum();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/votes")
    public Vote addVoteToPost(@PathVariable("id") int id, @RequestBody VoteDto voteDto,
                              @AuthenticationPrincipal Principal principal) {
        return postService.submitVote(voteDto, principal.getName(), id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Post createPost(@PathVariable("subredditId") int subredditId, @RequestBody PostDto postDto,
                           @AuthenticationPrincipal Principal principal) {
        return postService.addPost(postDto, principal.getName(), subredditId);
    }

    @PutMapping("{id}")
    public Post editPost(@PathVariable("id") int postId, @RequestBody PostDto postDto,
                         @AuthenticationPrincipal Principal principal) {
        return postService.editPost(postDto, postId, principal.getName());
    }
}
