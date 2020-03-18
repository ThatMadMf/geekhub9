package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.dtos.PostDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.services.CommentService;
import org.geekhub.reddit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/posts")
@RestController
public class PostController {

    private PostService postService;
    private CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
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
