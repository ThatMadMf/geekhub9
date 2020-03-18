package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Comment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private JdbcTemplate jdbcTemplate;

    public CommentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> getAllCommentsByPostId(int postId) {
        String sql = "select * from reddit.comments where post_id = ?";
        return jdbcTemplate.query(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Comment.class));
    }

    public List<Comment> getAllPostByUserLogin(String login) {
        String sql = "select * from reddit.comments where creator_login = ?";
        return jdbcTemplate.query(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Comment.class));
    }

    public Comment getCommentById(int commentId) {
        String sql = "select * from reddit.comments where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{commentId}, new BeanPropertyRowMapper<>(Comment.class));
    }

    public Comment addComment(Comment comment) {
        String sql = "insert into reddit.comments (creator_login, post_id, creation_date, content)" +
                " values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, comment.getCreatorLogin(), comment.getPostId(), comment.getCreationDate(),
                comment.getContent());
        return comment;
    }
}
