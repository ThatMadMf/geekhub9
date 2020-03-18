package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Vote;
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
        String sql = "select * from reddit.votes where post_id = ?";
        return jdbcTemplate.query(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Vote.class));
    }

    public List<Vote> getAllVotesByCommentId(int commentId) {
        String sql = "select * from reddit.votes where comment_id = ?";
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

    public Vote votePost(Vote vote) {
        if(voteExists(vote)) {
            throw new RuntimeException("Vote already exists");
        }
        String sql = "insert into reddit.votes (voter_login, post_id, vote_date, vote)" +
                " values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, vote.getVoterLogin(), vote.getPostId(), vote.getVoteDate(),
                vote.isUpvote());
        return vote;
    }

    public Vote voteComment(Vote vote) {
        if(voteExists(vote)) {
            throw new RuntimeException("Vote already exists");
        }
        String sql = "insert into reddit.votes (voter_login, comment_id, vote_date, vote)" +
                " values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, vote.getVoterLogin(), vote.getCommentId(), vote.getVoteDate(),
                vote.isUpvote());
        return vote;
    }

    private boolean voteExists(Vote vote) {
        String sql = "select exists (select * from reddit.votes where post_id = ? or comment_id = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{vote.getPostId(), vote.getCommentId()}, Boolean.class);
    }
}
