package org.geekhub.reddit.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.post.PostController;
import org.geekhub.reddit.post.PostDto;
import org.geekhub.reddit.post.PostService;
import org.geekhub.reddit.user.RegistrationService;
import org.geekhub.reddit.vote.PostVote;
import org.geekhub.reddit.vote.Vote;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PostControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @MockBean
    private PostService postService;

    @Autowired
    @MockBean
    private RegistrationService registrationService;

    @Test
    public void testGetAllPostBySubredditId() throws Exception {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post(new PostDto("title", "content"), 1, 1));
        postList.add(new Post(new PostDto("title2", "content2"), 2, 1));

        when(postService.getAllPostBySubredditId(1)).thenReturn(postList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/r/1/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post(new PostDto("title", "content"), 1, 1);

        when(postService.getPostById(1)).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/r/1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creatorId", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostVotes() throws Exception {
        List<Vote> voteList = new ArrayList<>();
        voteList.add(new PostVote(true, 1, 1));
        voteList.add(new PostVote(false, 2, 2));


        when(postService.getAllVotesByPostId(2)).thenReturn(voteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/r/1/posts/2/votes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].vote", is(false)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostVotesCount() throws Exception {

        when(postService.getVotesCount(4)).thenReturn(2);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/r/1/posts/4/votes-count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testAddVoteToPost() throws Exception {

        Vote vote = new PostVote(true, 22, 1);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        when(postService.submitVote(true, "author", 1)).thenReturn(vote);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/r/1/posts/1/votes")
                .principal(mockPrincipal)
                .content(objectMapper.writeValueAsString(true))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void testCreatePost() throws Exception {

        PostDto postDto = new PostDto("new post", "content");
        Post post = new Post(postDto, 1, 1);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        when(postService.addPost(postDto, "author", 1)).thenReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/r/1/posts")
                .principal(mockPrincipal)
                .content(objectMapper.writeValueAsString(postDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }
}