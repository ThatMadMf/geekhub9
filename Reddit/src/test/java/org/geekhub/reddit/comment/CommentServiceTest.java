package org.geekhub.reddit.comment;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.exception.NoRightsException;
import org.geekhub.reddit.user.RedditUser;
import org.geekhub.reddit.user.UserRepository;
import org.geekhub.reddit.vote.Vote;
import org.geekhub.reddit.vote.VoteApplicable;
import org.geekhub.reddit.vote.VoteService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@ActiveProfiles("test")
public class CommentServiceTest extends AbstractTestNGSpringContextTests {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private VoteService voteService;
    private CommentService commentService;

    private static final Comment comment = new Comment("CONTENT", 0, 1);

    private static final RedditUser redditUser = new RedditUser("login", LocalDate.now());

    private static final Vote vote = new Vote(true, 0, 1);

    private static final List<Comment> comments = new ArrayList<>(List.of(
            new Comment("FIRST_COMMENT", 2, 2),
            new Comment("SECOND_COMMENT", 5, 2)
    ));

    private static final List<Vote> votes = new ArrayList<>(List.of(
            new Vote(true, 3, 1),
            new Vote(true, 6, 1)
    ));

    @BeforeMethod
    public void setUp() {
        commentRepository = mock(CommentRepository.class);
        userRepository = mock(UserRepository.class);
        voteService = mock(VoteService.class);
        commentService = new CommentService(voteService, userRepository, commentRepository);
    }


    @Test
    public void testGetCommentById() throws Exception {
        when(commentRepository.getCommentById(1)).thenReturn(comment);

        assertEquals(commentService.getCommentById(1), comment);
    }

    @Test
    public void testGetAllVotesByCommentId() {
        when(voteService.getAllVotesByAppliedId(1, VoteApplicable.COMMENT)).thenReturn(votes);

        assertEquals(commentService.getAllVotesByCommentId(1), votes);
    }

    @Test
    public void testGetAllCommentsByPostId() {
        when(commentRepository.getCommentsByPostId(2)).thenReturn(comments);

        assertEquals(commentService.getAllCommentsByPostId(2), comments);

    }

    @Test
    public void testAddComment() {
        when(commentRepository.createComment(any(Comment.class))).thenReturn(1);
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);

        Comment comment = commentService.addComment("CONTENT", "login", 1);
        assertNotNull(comment);
        assertEquals(comment.getContent(), "CONTENT");
        assertEquals(comment.getPostId(), 1);
    }

    @Test
    public void testVoteComment() {
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);

        when(voteService.submitVote(any(Vote.class))).thenReturn(vote);

        assertEquals(commentService.voteComment(true, "login", 1), vote);
    }

    @Test
    public void testGetVotesCount() {
        when(voteService.getAllVotesByAppliedId(1, VoteApplicable.COMMENT)).thenReturn(votes);

        assertEquals(commentService.getVotesCount(1), 2);
    }

    @Test
    public void testEditComment() {
        Comment commentToReturn = new Comment("NEW COMMENT", 0, 1);

        when(commentRepository.getCommentById(1)).thenReturn(commentToReturn);
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);
        when(commentRepository.editComment("NEW COMMENT", 1)).thenReturn(1);

        Comment result = commentService.editComment("NEW COMMENT", "login", 1);

        assertNotNull(comment);
        assertEquals(result.getContent(), "NEW COMMENT");
        assertEquals(result.getPostId(), 1);
    }

    @Test(expectedExceptions = NoRightsException.class)
    public void testEditCommentShouldFail() {
        Comment noRightsComment = new Comment("NEW COMMENT", 2, 1);

        when(commentRepository.getCommentById(1)).thenReturn(noRightsComment);
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);

        Comment result = commentService.editComment("NEW COMMENT", "login", 1);
    }

    @Test
    public void testDeleteComment() {
        when(commentRepository.getCommentById(1)).thenReturn(comment);
        when(userRepository.getUserByLogin("login")).thenReturn(redditUser);

        commentService.deleteComment("login", 1);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_adding_comment_to_not_existing_post() {
        when(userRepository.getUserByLogin("fail")).thenReturn(redditUser);
        when(commentRepository.createComment(any(Comment.class))).thenReturn(0);

        commentService.addComment("Should", "fail", 222);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_edit_not_existing_comment() {
        when(commentRepository.getCommentById(22)).thenReturn(comment);
        when(userRepository.getUserByLogin("fail")).thenReturn(redditUser);
        when(commentRepository.editComment("COMMENT", 22)).thenReturn(0);

        commentService.editComment("Should", "fail", 22);
    }
}