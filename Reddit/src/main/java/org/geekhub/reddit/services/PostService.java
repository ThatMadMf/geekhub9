package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        return jdbcTemplate.queryForObject(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Post.class));
    }

    public Post addPost(Post post) {
        String sql = "insert into reddit.posts (creator_login, subreddit_id, title, creation_date, content)" +
                " values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, post.getCreatorLogin(), post.getSubredditId(), post.getTitle(), post.getCreationDate(),
                post.getContent());
        return post;
    }
}
