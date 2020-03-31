package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Comment;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.db.repositories.UserRepository;
import org.geekhub.reddit.dtos.VoteDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:templates/sql/comment_queries.properties")
public class CommentService {
    private final JdbcTemplate jdbcTemplate;
    private final VoteService voteService;
    private final Environment environment;
    private final UserRepository userRepository;

    public CommentService(JdbcTemplate jdbcTemplate, VoteService voteService, Environment environment,
                          UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.voteService = voteService;
        this.environment = environment;
        this.userRepository = userRepository;
    }

    public List<Vote> getAllVotesByCommentId(int id) {
        return voteService.getAllVotesByCommentId(id);
    }

    public List<Comment> getAllCommentsByPostId(int postId) {
        String sql = environment.getProperty("select-comment.postId");
        return jdbcTemplate.query(Objects.requireNonNull(sql),
                new Object[]{postId}, new BeanPropertyRowMapper<>(Comment.class));
    }

    public Comment addComment(String content, String authorName, int id) {
        Comment comment = new Comment(content, userRepository.getUserByLogin(authorName).getId(), id);
        String sql = environment.getProperty("insert-comment");
        jdbcTemplate.update(sql, comment.getCreatorId(), comment.getPostId(), comment.getCreationDate(),
                comment.getContent());
        return comment;
    }

    public Vote voteComment(VoteDto voteDto, String authorLogin, int id) {
        Vote vote = new Vote(voteDto, userRepository.getUserByLogin(authorLogin).getId(), id);
        return voteService.submitVote(vote);
    }
}
