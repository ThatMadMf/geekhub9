package org.geekhub.reddit.post;

import org.geekhub.reddit.exception.NoRightsException;
import org.geekhub.reddit.user.RedditUser;
import org.geekhub.reddit.user.UserRepository;
import org.geekhub.reddit.vote.Vote;
import org.geekhub.reddit.vote.VoteApplicable;
import org.geekhub.reddit.vote.VoteService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


public class PostServiceTest {

    private VoteService voteService;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostService postService;

    private static final Post post = new Post(
            new PostDto("TITLE OF POST", "CONTENT"),
            0,
            3);

    private static final Vote vote = new Vote(true, 0, 2);

    private static RedditUser redditUser = new RedditUser("login", LocalDate.now());

    private static final List<Post> posts = List.of(
            new Post(new PostDto("TITLE 1", "content of 1"), 0, 1),
            new Post(new PostDto("TITLE 2", "content of 2"), 0, 1)
    );

    private static final List<Vote> votes = List.of(
            new Vote(true, 0, 2),
            new Vote(true, 0, 2),
            new Vote(false, 0, 2)
    );

    @BeforeMethod
    public void setUp() {
        voteService = mock(VoteService.class);
        userRepository = mock(UserRepository.class);
        postRepository = mock(PostRepository.class);
        postService = new PostService(voteService, userRepository, postRepository);
    }

    @Test
    public void testGetAllPostBySubredditId() {
        when(postRepository.getPostsBySubredditId(1)).thenReturn(posts);

        assertEquals(postService.getAllPostBySubredditId(1), posts);
    }

    @Test
    public void testGetPostById() {
        when(postRepository.getPostById(3)).thenReturn(post);

        assertEquals(postService.getPostById(3), post);
    }

    @Test
    public void testAddPost() {
        when(postRepository.createPost(any(Post.class))).thenReturn(1);
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);

        Post createdPost = postService.addPost(new PostDto("title", "content"), "login", 3);
        assertNotNull(createdPost);
        assertEquals(createdPost.getContent(), "content");
        assertEquals(createdPost.getTitle(), "title");
        assertEquals(createdPost.getSubredditId(), 3);
    }

    @Test
    public void testSubmitVote() {
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(voteService.submitVote(any(Vote.class))).thenReturn(vote);

        Vote createdVote = postService.submitVote(true, "login", 2);

        assertEquals(createdVote, vote);
    }

    @Test
    public void testGetAllVotesByPostId() {
        when(voteService.getAllVotesByAppliedId(2, VoteApplicable.POST)).thenReturn(votes);

        List<Vote> retrievedVotes = postService.getAllVotesByPostId(2);

        assertEquals(retrievedVotes, votes);
    }

    @Test
    public void testEditPost() {
        PostDto postDto = new PostDto("TITLE OF POST", "some symbols");
        Post postToReturn = new Post(postDto, 0, 2);

        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(postRepository.getPostById(5)).thenReturn(postToReturn);
        when(postRepository.editPost(postDto, 5)).thenReturn(1);

        Post editedPost = postService.editPost(postDto, 5, "login");

        assertNotNull(editedPost);
        assertEquals(editedPost.getContent(), "some symbols");
        assertEquals(editedPost.getTitle(), "TITLE OF POST");
        assertEquals(editedPost.getSubredditId(), 2);
    }

    @Test
    public void testGetVotesCount() {
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(voteService.getAllVotesByAppliedId(2, VoteApplicable.POST)).thenReturn(votes);

        assertEquals(postService.getVotesCount(2), 1);
    }

    @Test
    public void testDeletePostContent() {
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(postRepository.getPostById(7)).thenReturn(post);
        postService.deletePostContent(7, "login");
    }

    @Test(expectedExceptions = NoRightsException.class)
    public void testDeletePostContentShouldFail() {
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(postRepository.getPostById(7)).thenReturn(new Post(new PostDto(), 2, 2));
        postService.deletePostContent(7, "login");
    }
}