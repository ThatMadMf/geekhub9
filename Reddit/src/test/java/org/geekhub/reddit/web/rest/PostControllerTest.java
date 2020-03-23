package org.geekhub.reddit.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.db.dtos.PostDto;
import org.geekhub.reddit.db.dtos.VoteDto;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.db.models.VoteApplicable;
import org.geekhub.reddit.services.PostService;
import org.geekhub.reddit.web.configuration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
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
        postList.add(new Post(new PostDto("login", 1, "title", "content")));
        postList.add(new Post(new PostDto("login2", 1, "title2", "content2")));

        when(postService.getAllPostBySubredditId(1)).thenReturn(postList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/subreddit/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetAllPostByUserLogin() throws Exception {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post(new PostDto("login", 1, "title", "content")));
        postList.add(new Post(new PostDto("login2", 1, "title2", "content2")));

        when(postService.getAllPostByUserLogin("login")).thenReturn(postList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/author/login")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post(new PostDto("login", 1, "title", "content"));

        when(postService.getPostById(1)).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creatorLogin", is("login")))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostVotes() throws Exception {
        List<Vote> voteList = new ArrayList<>();
        voteList.add(new Vote(new VoteDto("dude", true, VoteApplicable.POST), 1));
        voteList.add(new Vote(new VoteDto("man", false, VoteApplicable.POST), 2));


        when(postService.getAllVotesByPostId(2)).thenReturn(voteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/2/votes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].vote", is(false)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostVotesCount() throws Exception {
        List<Vote> voteList = new ArrayList<>();
        voteList.add(new Vote(new VoteDto("dude", true, VoteApplicable.POST), 1));
        voteList.add(new Vote(new VoteDto("man", true, VoteApplicable.POST), 2));


        when(postService.getAllVotesByPostId(2)).thenReturn(voteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/2/votes-count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testAddVoteToPost() throws Exception {
        VoteDto voteDto = new VoteDto("man", true, VoteApplicable.COMMENT);
        Vote vote = new Vote(voteDto, 1);


        when(postService.submitVote(vote)).thenReturn(vote);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/1/votes")
                .content(objectMapper.writeValueAsString(voteDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void testCreatePost() throws Exception {
        PostDto postDto = new PostDto("login", 1, "new post", "content");
        Post post = new Post(postDto);

        when(postService.addPost(post)).thenReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/")
                .content(objectMapper.writeValueAsString(postDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }
}