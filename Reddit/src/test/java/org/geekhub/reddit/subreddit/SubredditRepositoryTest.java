package org.geekhub.reddit.subreddit;

import org.geekhub.reddit.RedditMain;
import org.geekhub.reddit.db.configuration.DatabaseConfig;
import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.user.dto.RedditUser;
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
public class SubredditRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SubredditRepository subredditRepository;

    private static final int SUCCESS_UPDATE = 1;

    @Test
    public void testGetAllSubreddits() {
        List<Subreddit> subreddits = subredditRepository.getAllSubreddits();
        assertEquals(subreddits.get(0).getName(), "AskReddit");
        assertEquals(subreddits.get(1).getName(), "java");
    }

    @Test
    public void testGetSubscribers() {
        List<RedditUser> users = subredditRepository.getSubscribers(1);

        assertEquals(users.size(), 4);
        assertEquals(users.get(0).getLogin(), "admin");
        assertEquals(users.get(1).getLogin(), "dude");
    }

    @Test
    public void testGetSubredditById() {
        Subreddit subreddit = subredditRepository.getSubredditById(1);

        assertEquals(subreddit.getName(), "AskReddit");
        assertEquals(subreddit.getCreatorId(), 4);
        assertEquals(subreddit.getId(), 1);
    }

    @Test
    public void testCreateSubreddit() {
        Subreddit subreddit = new Subreddit("TEST SUBREDDIT", 1);

        assertEquals(subredditRepository.createSubreddit(subreddit), SUCCESS_UPDATE);
    }

    @Test
    public void testSubscribeUser() {
        assertEquals(subredditRepository.subscribeUser(2, 1), 1);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_getting_subreddit_with_invalid_id() {
        subredditRepository.getSubredditById(6969);
    }
}