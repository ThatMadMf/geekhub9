package org.geekhub.reddit.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.db.models.Subreddit;
import org.geekhub.reddit.services.SubredditService;
import org.geekhub.reddit.services.repositories.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubredditController.class)
public class SubredditControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @MockBean
    private SubredditService subredditService;

    @Autowired
    @MockBean
    private UserDetailsServiceImp userDetailsServiceImp;

    @Test
    public void testGetAllSubreddits() throws Exception {
        List<Subreddit> subreddits = new ArrayList<>();
        subreddits.add(new Subreddit("Subreddit 1", "user-creator", LocalDate.now()));
        subreddits.add(new Subreddit("Subreddit 2", "user-creator", LocalDate.now()));

        when(subredditService.getAllSubreddits()).thenReturn(subreddits);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subreddits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Subreddit 1")))
                .andExpect(jsonPath("$[1].creatorLogin", is("user-creator")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetSubredditById() throws Exception {
        Subreddit subreddit = new Subreddit(1, "Subreddit by id", "user-creator", LocalDate.now());

        when(subredditService.getSubredditById(1)).thenReturn(subreddit);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subreddits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Subreddit by id")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testCreateSubreddits() throws Exception {
        Subreddit subreddit = new Subreddit("AskReddit", "new post", LocalDate.now());

        when(subredditService.addSubreddit(subreddit)).thenReturn(subreddit);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subreddits")
                .content(objectMapper.writeValueAsString(subreddit))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }
}