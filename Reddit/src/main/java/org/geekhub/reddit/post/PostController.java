package org.geekhub.reddit.post;

import org.geekhub.reddit.vote.Vote;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/r/{subredditId}/posts")
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
        return postService.getVotesCount(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/votes")
    public Vote addVoteToPost(@PathVariable("id") int id, @RequestBody boolean vote,
                              @AuthenticationPrincipal Principal principal) {
        return postService.submitVote(vote, principal.getName(), id);
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") int postId, @AuthenticationPrincipal Principal principal) {
        postService.deletePostContent(postId, principal.getName());
    }
}


