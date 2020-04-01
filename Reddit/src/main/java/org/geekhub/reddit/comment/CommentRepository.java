package org.geekhub.reddit.comment;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    private static final String RESOURCE_PATH = "templates/sql/comment/";
    private static final String SELECT_ALL_BY_POST_ID = "select_postId.sql";
    private static final String INSERT_COMMENT = "insert.sql";

    private final BeanPropertyRowMapper<Comment> commentMapper;

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        commentMapper = new BeanPropertyRowMapper<>(Comment.class);
    }


    public List<Comment> getCommentsByPostId(int postId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_ALL_BY_POST_ID);
        return jdbcTemplate.query(sql, new Object[]{postId}, commentMapper);
    }

    public Comment createComment(Comment comment) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_COMMENT);
        jdbcTemplate.update(sql, comment.getCreatorId(), comment.getPostId(), comment.getCreationDate(),
                comment.getContent());
        return comment;
    }
}
