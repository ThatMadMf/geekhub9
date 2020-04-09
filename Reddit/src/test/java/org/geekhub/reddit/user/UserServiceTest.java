package org.geekhub.reddit.user;

import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.post.PostDto;
import org.geekhub.reddit.post.PostService;
import org.geekhub.reddit.subreddit.Subreddit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserServiceTest {

    private PostService postService;
    private UserRepository userRepository;
    private UserService userService;

    @BeforeMethod
    public void setUp() {
        postService = mock(PostService.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(postService, userRepository);
    }

    @Test
    public void testGetUser() {
        RedditUser redditUser = new RedditUser("login", LocalDate.now());

        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        RedditUser result = userService.getUser("login");

        assertEquals(result, redditUser);
    }

    @Test
    public void testGetUserFeed() {
        List<Subreddit> subreddits = List.of(
                new Subreddit("SUBREDDIT NO 1", 1),
                new Subreddit("SUBREDDIT NO 2", 2)
        );

        List<Post> firstPosts = List.of(
                new Post(new PostDto("TITLE 1", "symbols"), 4, 2),
                new Post(new PostDto("TITLE 2", "symbols"), 2, 2)
        );

        List<Post> secondPosts = List.of(
                new Post(new PostDto("TITLE 8", "symbols"), 3, 4)
        );

        subreddits.get(0).setId(2);
        subreddits.get(1).setId(4);

        when(userRepository.getSubscriptions(0)).thenReturn(subreddits);
        when(postService.getAllPostBySubredditId(2)).thenReturn(firstPosts);
        when(postService.getAllPostBySubredditId(4)).thenReturn(secondPosts);

        List<Post> feed = userService.getUserFeed(0);

        assertEquals(feed.size(), firstPosts.size() + secondPosts.size());
        assertTrue(feed.contains(firstPosts.get(0)));
        assertTrue(feed.contains(firstPosts.get(1)));
        assertTrue(feed.contains(secondPosts.get(0)));
    }

    @Test
    public void testGetUserPosts() {
        List<Post> posts = List.of(
                new Post(new PostDto("TITLE 1", "symbols"), 0, 2),
                new Post(new PostDto("TITLE 8", "symbols"), 0, 4),
                new Post(new PostDto("TITLE 2", "symbols"), 0, 2)
        );

        when(userRepository.getPosts(0)).thenReturn(posts);

        List<Post> result = userService.getUserPosts(0);

        assertEquals(result, posts);
    }

    @Test
    public void testDeleteUser() {

        userService.deleteUser(0);
    }

    @Test
    public void testGetUserPrivateData() {
        PrivateRedditUser privateRedditUser = new PrivateRedditUser(
                "login", LocalDate.now(),
                "USER",
                "mail"
        );

        when(userRepository.getUserInfo(0)).thenReturn(privateRedditUser);

        PrivateRedditUser result = userService.getUserPrivateData(0);

        assertEquals(result, privateRedditUser);
    }

    @Test
    public void testEditUser() {
        UserDto userDto = new UserDto("login", "mail");
        PrivateRedditUser privateRedditUser = new PrivateRedditUser(
                "login", LocalDate.now(),
                "USER",
                "mail"
        );

        when(userRepository.editUser(1, userDto)).thenReturn(privateRedditUser);

        assertEquals(userService.editUser(1, userDto), privateRedditUser);
    }

    @Test
    public void testEditUserRole() {
        PrivateRedditUser privateRedditUser = new PrivateRedditUser(
                "login", LocalDate.now(),
                "ADMIN",
                "mail"
        );

        when(userRepository.editUserRole(1, Role.ADMIN)).thenReturn(privateRedditUser);

        assertEquals(userService.editUserRole(1, Role.ADMIN), privateRedditUser);
    }
}