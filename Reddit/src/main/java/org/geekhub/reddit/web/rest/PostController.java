package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.dtos.PostDto;
import org.geekhub.reddit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/posts")
@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.addPost(new Post(postDto));
    }
}
