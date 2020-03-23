package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:templates/sql/comment_queries.properties")
public class CommentService {
    private JdbcTemplate jdbcTemplate;
    private VoteService voteService;
    private Environment environment;

    public CommentService(JdbcTemplate jdbcTemplate, VoteService voteService, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.voteService = voteService;
        this.environment = environment;
    }

    public List<Vote> getAllVotesByCommentId(int id) {
        return voteService.getAllVotesByCommentId(id);
    }

    public List<Comment> getAllCommentsByPostId(int postId) {
        String sql = environment.getProperty("select-comment.postId");
        return jdbcTemplate.query(Objects.requireNonNull(sql),
                new Object[]{postId}, new BeanPropertyRowMapper<>(Comment.class));
    }

    public Comment addComment(Comment comment) {
        String sql = environment.getProperty("insert-comment");
        jdbcTemplate.update(sql, comment.getCreatorLogin(), comment.getPostId(), comment.getCreationDate(),
                comment.getContent());
        return comment;
    }

    public Vote voteComment(Vote vote) {
        return voteService.submitVote(vote);
    }
}
