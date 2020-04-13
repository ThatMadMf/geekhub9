package org.geekhub.reddit.vote;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepository {

    private static final String RESOURCE_PATH = "templates/sql/vote/";
    private static final String INSERT_VOTE = getSql("insert.sql");
    private static final String SELECT_BY_APPLIED_ID = getSql("select_appliedId.sql");
    private static final String DELETE_BY_ID = getSql("delete_id.sql");

    private final BeanPropertyRowMapper<Vote> voteMapper;

    private final JdbcTemplate jdbcTemplate;

    public VoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        voteMapper = new BeanPropertyRowMapper<>(Vote.class);
    }

    public List<Vote> getVotesByAppliedId(int appliedId, VoteApplicable voteApplicable) {
        return jdbcTemplate.query(SELECT_BY_APPLIED_ID, new Object[]{appliedId, voteApplicable.name()}, voteMapper);
    }

    public Vote submitVote(Vote vote) {
        jdbcTemplate.update(INSERT_VOTE, vote.getVoterId(), vote.getVoteDate(), vote.getAppliedId(), vote.isVote(),
                vote.getVoteApplicable().name());
        return vote;
    }

    public int deleteVoteById(int id) {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private static String getSql(String fileName) {
        return ResourceReader.getSql(RESOURCE_PATH + fileName);
    }
}
