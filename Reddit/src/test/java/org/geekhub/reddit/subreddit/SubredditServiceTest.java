package org.geekhub.reddit.subreddit;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.post.PostService;
import org.geekhub.reddit.user.UserRepository;
import org.geekhub.reddit.user.dto.RedditUser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class SubredditServiceTest {

    private UserRepository userRepository;
    private SubredditRepository subredditRepository;
    private SubredditService subredditService;
    private PostService postService;

    @BeforeMethod
    public void setUp() {
        userRepository = mock(UserRepository.class);
        subredditRepository = mock(SubredditRepository.class);
        postService = mock(PostService.class);
        subredditService = new SubredditService(userRepository, subredditRepository, postService);
    }

    @Test
    public void testGetAllSubreddits() {
        List<Subreddit> subreddits = List.of(
                new Subreddit("SUBREDDIT NO 1", 1),
                new Subreddit("SUBREDDIT NO 2", 2),
                new Subreddit("SUBREDDIT NO 3", 1)
        );

        when(subredditRepository.getAllSubreddits()).thenReturn(subreddits);

        List<Subreddit> result = subredditService.getAllSubreddits();

        assertEquals(result, subreddits);
    }

    @Test
    public void testGetSubscribers() {
        List<RedditUser> redditUsers = List.of(
                new RedditUser("SUBSCRIBER 1", LocalDate.now()),
                new RedditUser("SUBSCRIBER 11", LocalDate.now())
        );

        when(subredditRepository.getSubscribers(2)).thenReturn(redditUsers);

        List<RedditUser> result = subredditService.getSubscribers(2);

        assertEquals(result, redditUsers);
    }

    @Test
    public void testGetSubredditById() {
        Subreddit subreddit = new Subreddit("SOME SUBREDDIT", 1);

        when(subredditRepository.getSubredditById(23)).thenReturn(subreddit);

        Subreddit result = subredditService.getSubredditById(23);

        assertEquals(result, subreddit);
    }

    @Test
    public void testAddSubreddit() {
        Subreddit subreddit = new Subreddit("CREATED SUBREDDIT", 0);
        RedditUser redditUser = new RedditUser("creator", LocalDate.now());

        when(subredditRepository.createSubreddit(any(Subreddit.class))).thenReturn(1);
        when(userRepository.getUserByLogin("creator")).thenReturn(redditUser);

        Subreddit createdSubreddit = subredditService.addSubreddit(
                "CREATED SUBREDDIT",
                "creator");

        assertEquals(createdSubreddit.getCreatorId(), subreddit.getCreatorId());
        assertEquals(createdSubreddit.getName(), subreddit.getName());
    }

    @Test
    public void testSubscribeUser() {
        RedditUser redditUser = new RedditUser("subscriber", LocalDate.now());

        when(subredditRepository.subscribeUser(1, 0)).thenReturn(1);
        when(userRepository.getUserByLogin("subscriber")).thenReturn(redditUser);

        subredditService.subscribeUser(1, "subscriber");
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_creating_subreddit() {
        RedditUser redditUser = new RedditUser("login", LocalDate.now());

        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(subredditRepository.createSubreddit(any(Subreddit.class))).thenReturn(0);

        subredditService.addSubreddit("Test", "login");
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_editing_not_existing_post() {
        RedditUser redditUser = new RedditUser("login", LocalDate.now());

        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(subredditRepository.subscribeUser(100, 0)).thenReturn(0);

        subredditService.subscribeUser(10, "login");
    }
}