package org.geekhub.reddit.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.db.dtos.SubredditDto;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.geekhub.reddit.services.SubredditService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubredditController.class)
@AutoConfigureMockMvc(addFilters = false)
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
    private RegistrationService registrationService;

    @Test
    public void testGetAllSubreddits() throws Exception {
        List<Subreddit> subreddits = new ArrayList<>();
        subreddits.add(new Subreddit(new SubredditDto("Subreddit 1", "user-creator")));
        subreddits.add(new Subreddit(new SubredditDto("Subreddit 2", "user-creator")));

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
        Subreddit subreddit = new Subreddit(new SubredditDto("Subreddit by id", "user-creator"));

        when(subredditService.getSubredditById(1)).thenReturn(subreddit);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subreddits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Subreddit by id")))
                .andExpect(jsonPath("$.creatorLogin", is("user-creator")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testCreateSubreddits() throws Exception {
        Subreddit subreddit = new Subreddit(new SubredditDto("AskReddit", "new post"));

        when(subredditService.addSubreddit(subreddit)).thenReturn(subreddit);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subreddits")
                .content(objectMapper.writeValueAsString(subreddit))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void testGetSubscribers() throws Exception {
        List<RedditUser> redditUsers = new ArrayList<>();
        redditUsers.add(new RedditUser("user1", LocalDate.now()));
        redditUsers.add(new RedditUser("user2", LocalDate.now()));

        when(subredditService.getSubscribers(1)).thenReturn(redditUsers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subreddits/1/subscribers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].login", is("user1")))
                .andExpect(jsonPath("$[1].login", is("user2")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testSubscribeUser() throws Exception {
        Subreddit subreddit = new Subreddit(new SubredditDto("AskReddit", "new post"));

        when(subredditService.subscribeUser(1, "user")).thenReturn(subreddit);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subreddits/1/subscribe")
                .content(objectMapper.writeValueAsString(subreddit))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }
}