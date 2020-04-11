package org.geekhub.reddit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.post.PostDto;
import org.geekhub.reddit.user.dto.PrivateRedditUser;
import org.geekhub.reddit.user.dto.RedditUser;
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
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @MockBean
    private UserService userService;

    @Autowired
    @MockBean
    private RegistrationService registrationService;

    @Test
    public void testGetUserFeed() throws Exception {
        RedditUser redditUser = new RedditUser("login", LocalDate.now());
        List<Post> posts = List.of(
                new Post(new PostDto("TITLE 1", "symbols"), 4, 2),
                new Post(new PostDto("TITLE 8", "symbols"), 3, 4),
                new Post(new PostDto("TITLE 2", "symbols"), 2, 2)
        );

        when(userService.getUser("login")).thenReturn(redditUser);
        when(userService.getUserFeed(0)).thenReturn(posts);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/feed")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    public void testGetUserPosts() throws Exception {
        RedditUser redditUser = new RedditUser("login", LocalDate.now());
        List<Post> posts = List.of(
                new Post(new PostDto("TITLE 1", "symbols"), 0, 2),
                new Post(new PostDto("TITLE 8", "symbols"), 0, 4),
                new Post(new PostDto("TITLE 2", "symbols"), 0, 2)
        );

        when(userService.getUser("login")).thenReturn(redditUser);
        when(userService.getUserPosts(0)).thenReturn(posts);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/posts")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    public void testGetPersonalData() throws Exception {
        RedditUser redditUser = new RedditUser("login", LocalDate.now());
        PrivateRedditUser privateRedditUser = new PrivateRedditUser("login", LocalDate.now(), "USER", "mail");

        when(userService.getUser("login")).thenReturn(redditUser);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        when(userService.getUserPrivateData(0)).thenReturn(privateRedditUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.login", is(privateRedditUser.getLogin())))
                .andExpect(jsonPath("$.role", is(privateRedditUser.getRole())))
                .andExpect(jsonPath("$.email", is(privateRedditUser.getEmail())))
                .andDo(print());
    }

    @Test
    public void testDeleteUser() throws Exception {

        RedditUser redditUser = new RedditUser("login", LocalDate.now());
        when(userService.getUser("login")).thenReturn(redditUser);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204))
                .andDo(print());
    }
}