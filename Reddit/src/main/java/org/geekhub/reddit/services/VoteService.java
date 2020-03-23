package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.exception.DataBaseRowException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private JdbcTemplate jdbcTemplate;

    public VoteService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Vote> getAllVotesByPostId(int postId) {
        String sql = "select * from reddit.votes where applied_id = ? and vote_applicable = 'POST'";
        return jdbcTemplate.query(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public List<Vote> getAllVotesByCommentId(int commentId) {
        String sql = "select * from reddit.votes where applied_id = ? and vote_applicable = 'COMMENT'";
        return jdbcTemplate.query(sql, new Object[]{commentId}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public List<Vote> getAllVotesByUserLogin(String login) {
        String sql = "select * from reddit.votes where creator_login = ?";
        return jdbcTemplate.query(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public Vote getVoteById(int voteId) {
        String sql = "select * from reddit.comments where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{voteId}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public Vote submitVote(Vote vote) {
        if (voteExists(vote)) {
            throw new DataBaseRowException("Vote already exists");
        }
        String sql = "insert into reddit.votes (voter_login, vote_date, applied_id, vote, vote_applicable)" +
                " values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, vote.getVoterLogin(), vote.getVoteDate(), vote.getAppliedId(), vote.isVote(),
                vote.getVoteApplicable().name());
        return vote;
    }

    private boolean voteExists(Vote vote) {
        String sql = "select exists (select v.* from reddit.votes v where v.applied_id = ? and v.vote_applicable = ? " +
                "and v.voter_login = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{vote.getAppliedId(), vote.getVoteApplicable().name(),
                vote.getVoterLogin()}, Boolean.class);
    }

    public void deleteVote(Vote vote) {
        if (!voteExists(vote)) {
            throw new DataBaseRowException("Vote is absent in database");
        }
        String sql = "delete v.* from reddit.votes v where v.id = ?";
        jdbcTemplate.update(sql, vote.getId());
    }
}
