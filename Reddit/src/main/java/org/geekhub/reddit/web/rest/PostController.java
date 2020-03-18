package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.dtos.PostDto;
import org.geekhub.reddit.db.dtos.VoteDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.services.CommentService;
import org.geekhub.reddit.services.PostService;
import org.geekhub.reddit.services.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/posts")
@RestController
public class PostController {

    private PostService postService;
    private CommentService commentService;
    private VoteService voteService;

    public PostController(PostService postService, CommentService commentService, VoteService voteService) {
        this.postService = postService;
        this.commentService = commentService;
        this.voteService = voteService;
    }

    @GetMapping("subreddit")
    @ResponseBody
    public List<Post> getAllPostBySubredditId(@RequestParam("subredditId") int subredditId) {
        return postService.getAllPostBySubredditId(subredditId);
    }


    @GetMapping("author")
    @ResponseBody
    public List<Post> getAllPostByUserLogin(@RequestParam("author") String creatorLogin) {
        return postService.getAllPostByUserLogin(creatorLogin);
    }

    @GetMapping("{id}")
    @ResponseBody
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @GetMapping("{id}/comments")
    public List<Comment> getPostComments(@PathVariable("id") int id) {
        return  commentService.getAllCommentsByPostId(id);
    }

    @GetMapping("{post}/comments/{id}/votes")
    public List<Vote> getPostCommentVotes(@PathVariable("id") int id) {
        return voteService.getAllVotesByCommentId(id);
    }

    @GetMapping("{id}/votes")
    public List<Vote> getPostVotes(@PathVariable("id") int id) {
        return voteService.getAllVotesByPostId(id);
    }

    @GetMapping("{id}/votes-count")
    public int getPostVotesCount(@PathVariable("id") int id) {
        return voteService.getAllVotesByPostId(id).size();
    }

    @GetMapping("{post}/comment/{id}/votes-count")
    public int getCommentVotesCount(@PathVariable("id") int id) {
        return voteService.getAllVotesByCommentId(id).size();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{post}/comments/{id}/votes")
    public Vote addVoteToComment(@PathVariable("id") int id, @RequestBody VoteDto voteDto) {
        return voteService.voteComment(new Vote(voteDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/votes")
    public Vote addVoteToPost(@PathVariable("id") int id, @RequestBody VoteDto voteDto) {
        return voteService.votePost(new Vote(voteDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/comments")
    public Comment addCommentToPost(@PathVariable("id") int id, @RequestBody CommentDto commentDto) {
        return commentService.addComment(new Comment(commentDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.addPost(new Post(postDto));
    }
}
