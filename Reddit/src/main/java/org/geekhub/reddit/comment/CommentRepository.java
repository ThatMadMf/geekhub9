package org.geekhub.reddit.comment;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.util.ResourceReader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    private static final String RESOURCE_PATH = "static/sql/comment/";
    private static final String INSERT_COMMENT = getSql("insert.sql");
    private static final String SELECT_BY_ID = getSql("select_id.sql");
    private static final String UPDATE_BY_ID = getSql("update_id.sql");
    private static final String DELETE_BY_ID = getSql("delete_id.sql");
    private static final String SELECT_BY_POST_ID = getSql("select_postId.sql");

    private final BeanPropertyRowMapper<Comment> commentMapper;

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        commentMapper = new BeanPropertyRowMapper<>(Comment.class);
    }

    public List<Comment> getCommentsByPostId(int postId) {
        return jdbcTemplate.query(SELECT_BY_POST_ID, new Object[]{postId}, commentMapper);
    }

    public int createComment(Comment comment) {
        try {
            return jdbcTemplate.update(INSERT_COMMENT, comment.getCreatorId(), comment.getPostId(),
                    comment.getCreationDate(), comment.getContent());
        } catch (DataIntegrityViolationException ex) {
            throw new DataBaseRowException("Post with the id does not exist");
        }
    }

    public Comment getCommentById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, commentMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataBaseRowException("Comment with the id do not exists");
        }
    }

    public int editComment(String content, int id) {
        return jdbcTemplate.update(UPDATE_BY_ID, content, id);
    }

    public int deleteComment(int id) {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private static String getSql(String fileName) {
        return ResourceReader.getSql(RESOURCE_PATH + fileName);
    }
}
