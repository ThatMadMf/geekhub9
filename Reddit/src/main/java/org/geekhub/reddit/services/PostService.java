package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:templates/sql/post_queries.properties")
public class PostService {

    private JdbcTemplate jdbcTemplate;
    private CommentService commentService;
    private VoteService voteService;
    private Environment environment;

    public PostService(JdbcTemplate jdbcTemplate, CommentService commentService,
                       VoteService voteService, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentService = commentService;
        this.voteService = voteService;
        this.environment = environment;
    }

    public List<Post> getAllPostBySubredditId(int subredditId) {
        String sql = environment.getRequiredProperty("select-post.subredditId");
        return jdbcTemplate.query(sql, new Object[]{subredditId}, new BeanPropertyRowMapper<>(Post.class));
    }

    public List<Post> getAllPostByUserLogin(String login) {
        String sql = environment.getRequiredProperty("select-post.userLogin");
        return jdbcTemplate.query(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Post.class));
    }

    public Post getPostById(int postId) {
        String sql = environment.getRequiredProperty("select-post.id");
        Post post = jdbcTemplate.queryForObject(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Post.class));
        post.setComments(commentService.getAllCommentsByPostId(postId));
        return post;
    }

    public Post addPost(Post post) {
        String sql = environment.getRequiredProperty("insert-post");
        jdbcTemplate.update(sql, post.getCreatorLogin(), post.getSubredditId(), post.getTitle(), post.getCreationDate(),
                post.getContent());
        return post;
    }

    public Vote submitVote(Vote vote) {
        return voteService.submitVote(vote);
    }

    public List<Vote> getAllVotesByPostId(int id) {
        return voteService.getAllVotesByPostId(id);
    }
}
