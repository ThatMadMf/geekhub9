package org.geekhub.reddit.comment;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    private static final String RESOURCE_PATH = "templates/sql/comment/";
    private static final String INSERT_COMMENT = "insert.sql";
    private static final String SELECT_BY_ID = "select_id.sql";
    private static final String UPDATE_BY_ID = "update_id.sql";
    private static final String DELETE_BY_ID = "delete_id.sql";
    private static final String SELECT_BY_POST_ID = "select_postId.sql";

    private final BeanPropertyRowMapper<Comment> commentMapper;

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        commentMapper = new BeanPropertyRowMapper<>(Comment.class);
    }


    public List<Comment> getCommentsByPostId(int postId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_POST_ID);
        return jdbcTemplate.query(sql, new Object[]{postId}, commentMapper);
    }

    public Comment createComment(Comment comment) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_COMMENT);
        jdbcTemplate.update(sql, comment.getCreatorId(), comment.getPostId(), comment.getCreationDate(),
                comment.getContent());
        return comment;
    }

    public Comment getCommentById(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_ID);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, commentMapper);
    }


    public Comment editComment(String content, int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + UPDATE_BY_ID);
        jdbcTemplate.update(sql, content, id);

        return getCommentById(id);
    }

    public void deleteComment(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + DELETE_BY_ID);

        jdbcTemplate.update(sql, id);
    }
}
