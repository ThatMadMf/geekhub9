package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.exception.DataBaseRowException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:templates/sql/vote_queries.properties")
public class VoteService {

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    public VoteService(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    public List<Vote> getAllVotesByPostId(int postId) {
        String sql = environment.getRequiredProperty("select-vote.postId");
        return jdbcTemplate.query(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public List<Vote> getAllVotesByCommentId(int commentId) {
        String sql = environment.getRequiredProperty("select-vote.commentId");
        return jdbcTemplate.query(sql, new Object[]{commentId}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public Vote submitVote(Vote vote) {
        if (voteExists(vote)) {
            throw new DataBaseRowException("Vote already exists");
        }
        String sql = environment.getRequiredProperty("insert-vote");
        jdbcTemplate.update(sql, vote.getVoterLogin(), vote.getVoteDate(), vote.getAppliedId(), vote.isVote(),
                vote.getVoteApplicable().name());
        return vote;
    }

    private boolean voteExists(Vote vote) {
        String sql = environment.getRequiredProperty("select-exists-vote");
        return jdbcTemplate.queryForObject(sql, new Object[]{vote.getAppliedId(), vote.getVoteApplicable().name(),
                vote.getVoterLogin()}, Boolean.class);
    }

    public void deleteVote(Vote vote) {
        if (!voteExists(vote)) {
            throw new DataBaseRowException("Vote is absent in database");
        }
        String sql = environment.getRequiredProperty("delete-vote");
        jdbcTemplate.update(sql, vote.getId());
    }
}
