package org.geekhub.reddit.comment;

import org.geekhub.reddit.RedditMain;
import org.geekhub.reddit.db.configuration.DatabaseConfig;
import org.geekhub.reddit.exception.DataBaseRowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = {RedditMain.class, DatabaseConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CommentRepositoryTest extends AbstractTestNGSpringContextTests {

    private static final Comment first_comment = new Comment("FIRST_COMMENT", 1, 4);
    private static final Comment second_comment = new Comment("COMMENT_No_2", 1, 4);
    private static final int SUCCESS_UPDATE = 1;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Transactional
    public void testGetCommentsByPostId() {

        commentRepository.createComment(first_comment);
        commentRepository.createComment(second_comment);

        List<Comment> result = commentRepository.getCommentsByPostId(4);
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getContent(), first_comment.getContent());
        assertEquals(result.get(0).getCreatorId(), first_comment.getCreatorId());
        assertEquals(result.get(1).getContent(), second_comment.getContent());
        assertEquals(result.get(1).getCreatorId(), second_comment.getCreatorId());
    }

    @Test
    @Transactional
    public void testCreateComment() {
        assertEquals(commentRepository.createComment(
                new Comment("TEST", 1, 1)
        ), SUCCESS_UPDATE);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void testGetCommentByIdShouldFail() {
        commentRepository.getCommentById(967);
    }

    @Test
    public void testEditCommentShouldReturnZero() {
        assertEquals(commentRepository.editComment("NEW CONTENT", 874), 0);
    }

    @Test
    public void testDeleteComment() {

        assertEquals(commentRepository.deleteComment(1), 1);
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void throwing_exception_when_creating_comment_with_not_existing_post() {

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        doThrow(EmptyResultDataAccessException.class).when(jdbcTemplate).update(
                anyString(),
                any(Object[].class)
        );

        commentRepository.createComment(new Comment("SHOULD FAIL", 1, 6969));
    }
}