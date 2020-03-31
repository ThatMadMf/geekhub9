package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.db.repositories.UserRepository;
import org.geekhub.reddit.dtos.PostDto;
import org.geekhub.reddit.dtos.VoteDto;
import org.geekhub.reddit.exception.NoRightsException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:templates/sql/post_queries.properties")
public class PostService {

    private final JdbcTemplate jdbcTemplate;
    private final CommentService commentService;
    private final VoteService voteService;
    private final Environment environment;
    private final UserRepository userRepository;

    public PostService(JdbcTemplate jdbcTemplate, CommentService commentService,
                       VoteService voteService, Environment environment, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentService = commentService;
        this.voteService = voteService;
        this.environment = environment;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPostBySubredditId(int subredditId) {
        String sql = environment.getRequiredProperty("select-post.subredditId");
        return jdbcTemplate.query(sql, new Object[]{subredditId}, new BeanPropertyRowMapper<>(Post.class));
    }

    public List<Post> getAllPostByUserLogin(String login) {
        String sql = environment.getRequiredProperty("select-post.userLogin");
        return jdbcTemplate.query(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Post.class));
    }

    public Post getPostById(int postId) {
        String sql = environment.getRequiredProperty("select-post.id");
        Post post = jdbcTemplate.queryForObject(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Post.class));
        post.setComments(commentService.getAllCommentsByPostId(postId));
        return post;
    }

    public Post addPost(PostDto postDto, String authorLogin, int id) {
        Post post = new Post(postDto, userRepository.getUserByLogin(authorLogin).getId(), id);
        String sql = environment.getRequiredProperty("insert-post");
        jdbcTemplate.update(sql, post.getCreatorId(), post.getSubredditId(), post.getTitle(), post.getCreationDate(),
                post.getContent());
        return post;
    }

    public Vote submitVote(VoteDto voteDto, String authorLogin, int id) {
        Vote vote = new Vote(voteDto, userRepository.getUserByLogin(authorLogin).getId(), id);
        return voteService.submitVote(vote);
    }

    public List<Vote> getAllVotesByPostId(int id) {
        return voteService.getAllVotesByPostId(id);
    }


    public Post editPost(PostDto postDto, int postId, String editorLogin) {
        Post editedPost = getPostById(postId);
        if (editedPost.getCreatorId() != userRepository.getUserByLogin(editorLogin).getId()) {
            throw new NoRightsException("You have no rights to edit this post");
        }

        String sql = environment.getRequiredProperty("update-post.id");
        jdbcTemplate.update(sql, postDto.getTitle(), postDto.getContent(), postId);
        return getPostById(postId);
    }
}
