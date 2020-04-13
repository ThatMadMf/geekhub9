package org.geekhub.reddit.vote;

import org.geekhub.reddit.RedditMain;
import org.geekhub.reddit.db.configuration.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {RedditMain.class, DatabaseConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class VoteRepositoryTest extends AbstractTestNGSpringContextTests {

    private static final int OPERATION_SUCCESS_STATUS = 1;
    private static final int NOT_EXISTING_ID = 13566531;
    private static final int OPERATION_FAILURE_STATUS = 0;

    @Autowired
    VoteRepository voteRepository;

    @Test
    public void testGetVotesByAppliedId() {
        List<Vote> votes = voteRepository.getVotesByAppliedId(2, VoteApplicable.POST);

        assertEquals(votes.size(), 4);
    }

    @Test
    public void testSubmitVote() {
        int votesSize = voteRepository.getVotesByAppliedId(1, VoteApplicable.POST).size();
        assertEquals(votesSize, 0);

        Vote vote = new PostVote(true, 3, 1);
        voteRepository.submitVote(vote);

        List<Vote> result = voteRepository.getVotesByAppliedId(1, VoteApplicable.POST);
        assertEquals(result.size(), 1);
    }

    @Test
    public void testDeleteVoteById() {
        assertEquals(voteRepository.deleteVoteById(NOT_EXISTING_ID), OPERATION_FAILURE_STATUS);
    }
}