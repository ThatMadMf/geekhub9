package org.geekhub.reddit.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.dtos.VoteDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.db.models.VoteApplicable;
import org.geekhub.reddit.services.CommentService;
import org.geekhub.reddit.web.configuration.RegistrationService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    @MockBean
    private CommentService commentService;

    @Autowired
    @MockBean
    private RegistrationService registrationService;


    @Test
    public void testGetPostComments() throws Exception {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment("text", "user", 3));
        commentList.add(new Comment("text text", "man", 3));


        when(commentService.getAllCommentsByPostId(3)).thenReturn(commentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/3/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].content", is("text text")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetPostCommentVotes() throws Exception {
        List<Vote> voteList = new ArrayList<>();
        voteList.add(new Vote(new VoteDto(true, VoteApplicable.COMMENT), "dude", 1));
        voteList.add(new Vote(new VoteDto(true, VoteApplicable.COMMENT), "man", 2));


        when(commentService.getAllVotesByCommentId(3)).thenReturn(voteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1/comments/3/votes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].voterLogin", is("man")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void testGetCommentVotesCount() throws Exception {
        List<Vote> voteList = new ArrayList<>();
        voteList.add(new Vote(new VoteDto(true, VoteApplicable.COMMENT), "dude", 1));
        voteList.add(new Vote(new VoteDto(true, VoteApplicable.COMMENT), "man", 2));
        voteList.add(new Vote(new VoteDto(true, VoteApplicable.COMMENT), "man2", 3));

        when(commentService.getAllVotesByCommentId(1)).thenReturn(voteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/2/comments/1/votes-count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    public void testAddCommentToPost() throws Exception {
        Comment comment = new Comment("CONTENT", "login", 1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        when(commentService.addComment(comment)).thenReturn(comment);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/1/comments")
                .principal(mockPrincipal)
                .content(objectMapper.writeValueAsString("CONTENT"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void testAddVoteToComment() throws Exception {
        VoteDto voteDto = new VoteDto(true, VoteApplicable.COMMENT);
        Vote vote = new Vote(voteDto, "man", 1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("login");

        when(commentService.voteComment(vote)).thenReturn(vote);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/2/comments/1/votes")
                .principal(mockPrincipal)
                .content(objectMapper.writeValueAsString(voteDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }
}