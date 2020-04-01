package org.geekhub.reddit.vote;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepository {

    private static final String RESOURCE_PATH = "templates/sql/vote/";
    private static final String SELECT_ALL_BY_POST_ID = "select_postId.sql";
    private static final String SELECT_ALL_BY_COMMENT_ID = "select_commentId.sql";
    private static final String INSERT_VOTE = "insert.sql";
    private static final String SELECT_EXISTS_BY_ID = "select-exists_id.sql";
    private static final String DELETE_BY_ID = "delete.sql";

    private final BeanPropertyRowMapper<Vote> voteMapper;

    private final JdbcTemplate jdbcTemplate;

    public VoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        voteMapper = new BeanPropertyRowMapper<>(Vote.class);
    }

    public List<Vote> getVotesByPostId(int postId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_ALL_BY_POST_ID);
        return jdbcTemplate.query(sql, new Object[]{postId}, voteMapper);
    }

    public List<Vote> getVotesByCommentId(int commentId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_ALL_BY_COMMENT_ID);
        return jdbcTemplate.query(sql, new Object[]{commentId}, voteMapper);
    }

    public Vote submitVote(Vote vote) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_VOTE);
        jdbcTemplate.update(sql, vote.getVoterId(), vote.getVoteDate(), vote.getAppliedId(), vote.isVote(),
                vote.getVoteApplicable().name());
        return vote;
    }

    public boolean voteExists(Vote vote) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_EXISTS_BY_ID);
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, vote.getAppliedId(),
                vote.getVoteApplicable().name(), vote.getVoterId());
        return count != null && count > 0;
    }

    public void deleteVote(Vote vote) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + DELETE_BY_ID);
        jdbcTemplate.update(sql, vote.getId());
    }
}
