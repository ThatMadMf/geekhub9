package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private JdbcTemplate jdbcTemplate;
    private CommentService commentService;
    private VoteService voteService;

    public PostService(JdbcTemplate jdbcTemplate, CommentService commentService, VoteService voteService) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentService = commentService;
        this.voteService = voteService;
    }

    public List<Post> getAllPostBySubredditId(int subredditId) {
        String sql = "select * from reddit.posts where subreddit_id = ?";
        return jdbcTemplate.query(sql, new Object[]{subredditId}, new BeanPropertyRowMapper<>(Post.class));
    }

    public List<Post> getAllPostByUserLogin(String login) {
        String sql = "select * from reddit.posts where creator_login = ?";
        return jdbcTemplate.query(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Post.class));
    }

    public Post getPostById(int postId) {
        String sql = "select * from reddit.posts where id = ?";
        Post post = jdbcTemplate.queryForObject(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Post.class));
        post.setComments(commentService.getAllCommentsByPostId(postId));
        return post;
    }

    public Post addPost(Post post) {
        String sql = "insert into reddit.posts (creator_login, subreddit_id, title, creation_date, content)" +
                " values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, post.getCreatorLogin(), post.getSubredditId(), post.getTitle(), post.getCreationDate(),
                post.getContent());
        return post;
    }

    public List<Comment> getAllCommentsByPostId(int id) {
        return commentService.getAllCommentsByPostId(id);
    }

    public Vote votePost(Vote vote) {
        return voteService.vote(vote);
    }

    public Comment addComment(Comment comment) {
        return commentService.addComment(comment);
    }

    public List<Vote> getAllVotesByPostId(int id) {
        return voteService.getAllVotesByPostId(id);
    }
}
