package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("post")
    @ResponseBody
    public List<Comment> getAllCommentsByPostId(@RequestParam("postId") int postId) {
        return commentService.getAllCommentsByPostId(postId);
    }


    @GetMapping("author")
    @ResponseBody
    public List<Comment> getAllCommentsByUserLogin(@RequestParam("author") String creatorLogin) {
        return commentService.getAllPostByUserLogin(creatorLogin);
    }

    @GetMapping("{id}")
    @ResponseBody
    public Comment getSubredditById(@PathVariable int id) {
        return commentService.getCommentById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment createComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(new Comment(commentDto));
    }
}
