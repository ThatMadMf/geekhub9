package org.geekhub.reddit.post;

import org.geekhub.reddit.RedditMain;
import org.geekhub.reddit.db.configuration.DatabaseConfig;
import org.geekhub.reddit.exception.DataBaseRowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = {RedditMain.class, DatabaseConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PostRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    PostRepository postRepository;

    private static final int NOT_EXISTING_POST_ID = 874;

    private static final PostDto POST_DTO = new PostDto("TITLE", "CONTENT");
    private static final Post FIRST_POST = new Post(POST_DTO, 1, 4);
    private static final Post SECOND_POST = new Post(POST_DTO, 2, 4);


    @Test
    public void testGetPostsBySubredditId() {
        postRepository.createPost(FIRST_POST);
        postRepository.createPost(SECOND_POST);

        List<Post> result = postRepository.getPostsBySubredditId(4);
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getSubredditId(), FIRST_POST.getSubredditId());
        assertEquals(result.get(0).getCreatorId(), FIRST_POST.getCreatorId());
        assertEquals(result.get(1).getSubredditId(), SECOND_POST.getSubredditId());
        assertEquals(result.get(1).getCreatorId(), SECOND_POST.getCreatorId());
    }

    @Test(expectedExceptions = DataBaseRowException.class)
    public void testGetPostByIdShouldFail() {
        postRepository.getPostById(NOT_EXISTING_POST_ID);
    }

    @Test
    public void testCreatePost() {
        assertEquals(postRepository.createPost(
                new Post(POST_DTO, 3, 1)
        ), 1);
    }

    @Test
    public void testEditPost() {
        assertEquals(postRepository.editPost(POST_DTO, NOT_EXISTING_POST_ID), 0);
    }

    @Test
    public void testDeletePost() {
        assertEquals(postRepository.deletePost(-1), 0);
    }
}