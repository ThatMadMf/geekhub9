package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.dtos.PostDto;
import org.geekhub.reddit.db.dtos.VoteDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("subreddit/{id}")
    @ResponseBody
    public List<Post> getAllPostBySubredditId(@PathVariable("id") int subredditId) {
        return postService.getAllPostBySubredditId(subredditId);
    }


    @GetMapping("author/{id}")
    @ResponseBody
    public List<Post> getAllPostByUserLogin(@PathVariable("id") String creatorLogin) {
        return postService.getAllPostByUserLogin(creatorLogin);
    }

    @GetMapping("{id}")
    @ResponseBody
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @GetMapping("{id}/comments")
    public List<Comment> getPostComments(@PathVariable("id") int id) {
        return postService.getAllCommentsByPostId(id);
    }

    @GetMapping("{post}/comments/{id}/votes")
    public List<Vote> getPostCommentVotes(@PathVariable("id") int id) {
        return postService.getAllVotesByCommentId(id);
    }

    @GetMapping("{id}/votes")
    public List<Vote> getPostVotes(@PathVariable("id") int id) {
        return postService.getAllVotesByPostId(id);
    }

    @GetMapping("{id}/votes-count")
    public int getPostVotesCount(@PathVariable("id") int id) {
        return postService.getAllVotesByPostId(id).size();
    }

    @GetMapping("{post}/comments/{id}/votes-count")
    public int getCommentVotesCount(@PathVariable("id") int id) {
        return postService.getAllVotesByCommentId(id).size();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "{post}/comments/{id}/votes")
    public Vote addVoteToComment(@PathVariable("id") int id, @RequestBody VoteDto voteDto) {
        return postService.voteComment(new Vote(voteDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/votes")
    public Vote addVoteToPost(@PathVariable("id") int id, @RequestBody VoteDto voteDto) {
        return postService.votePost(new Vote(voteDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/comments")
    public Comment addCommentToPost(@PathVariable("id") int id, @RequestBody CommentDto commentDto) {
        return postService.addComment(new Comment(commentDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.addPost(new Post(postDto));
    }
}
