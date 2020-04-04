package org.geekhub.reddit.vote;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepository {

    private static final String RESOURCE_PATH = "templates/sql/vote/";
    private static final String INSERT_VOTE = "insert.sql";
    private static final String SELECT_BY_APPLIED_ID = "select_appliedId.sql";
    private static final String DELETE_BY_ID = "delete_id.sql";

    private final BeanPropertyRowMapper<Vote> voteMapper;

    private final JdbcTemplate jdbcTemplate;

    public VoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        voteMapper = new BeanPropertyRowMapper<>(Vote.class);
    }

    public List<Vote> getVotesByAppliedId(int appliedId, VoteApplicable voteApplicable) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_APPLIED_ID);
        return jdbcTemplate.query(sql, new Object[]{appliedId, voteApplicable.name()}, voteMapper);
    }

    public Vote submitVote(Vote vote) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_VOTE);
        jdbcTemplate.update(sql, vote.getVoterId(), vote.getVoteDate(), vote.getAppliedId(), vote.isVote(),
                vote.getVoteApplicable().name());
        return vote;
    }

    public void deleteVoteById(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + DELETE_BY_ID);
        jdbcTemplate.update(sql, id);
    }
}
