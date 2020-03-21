package org.geekhub.reddit.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.reddit.db.dtos.CommentDto;
import org.geekhub.reddit.db.dtos.VoteDto;
import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
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

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest extends AbstractTestNGSpringContextTests {

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
    public void testGetPostComments() throws Exception {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment(new CommentDto("user", 3, "text")));
        commentList.add(new Comment(new CommentDto("man", 3, "text text")));


        when(postService.getAllCommentsByPostId(3)).thenReturn(commentList);
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
        voteList.add(new Vote(new VoteDto("user", true, 0, 3)));
        voteList.add(new Vote(new VoteDto("man", true, 0, 3)));


        when(postService.getAllVotesByCommentId(3)).thenReturn(voteList);
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
        voteList.add(new Vote(new VoteDto("user", true, 0, 1)));
        voteList.add(new Vote(new VoteDto("man", true, 0, 1)));
        voteList.add(new Vote(new VoteDto("man", true, 0, 1)));


        when(postService.getAllVotesByCommentId(1)).thenReturn(voteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/2/comments/1/votes-count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    public void testAddCommentToPost() throws Exception {
        CommentDto commentDto = new CommentDto("login", 1, "CONTENT");
        Comment comment = new Comment(commentDto);

        when(postService.addComment(comment)).thenReturn(comment);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/1/comments")
                .content(objectMapper.writeValueAsString(commentDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void testAddVoteToComment() throws Exception {
        VoteDto voteDto = new VoteDto("user", true, 0, 1);
        Vote vote = new Vote(voteDto);


        when(postService.voteComment(vote)).thenReturn(vote);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/2/comments/1/votes")
                .content(objectMapper.writeValueAsString(voteDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(print());
    }
}