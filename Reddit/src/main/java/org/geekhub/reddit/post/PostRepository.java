package org.geekhub.reddit.post;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    private static final String RESOURCE_PATH = "templates/sql/post/";
    private static final String SELECT_ALL_BY_SUBREDDIT_ID = "select_subredditId.sql";
    private static final String SELECT_BY_ID = "select_id.sql";
    private static final String INSERT_POST = "insert.sql";
    private static final String UPDATE_POST_BY_ID = "update_id.sql";

    private final BeanPropertyRowMapper<Post> postMapper;
    private final JdbcTemplate jdbcTemplate;


    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        postMapper = new BeanPropertyRowMapper<>(Post.class);
    }

    public List<Post> getPostsBySubredditId(int subredditId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_ALL_BY_SUBREDDIT_ID);
        return jdbcTemplate.query(sql, new Object[]{subredditId}, postMapper);
    }

    public Post getPostById(int postId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_ID);
        return jdbcTemplate.queryForObject(sql, new Object[]{postId}, postMapper);
    }

    public Post createPost(Post post) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_POST);
        jdbcTemplate.update(sql, post.getCreatorId(), post.getSubredditId(), post.getTitle(), post.getCreationDate(),
                post.getContent());
        return post;
    }

    public Post editPost(PostDto postDto, int postId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + UPDATE_POST_BY_ID);
        jdbcTemplate.update(sql, postDto.getTitle(), postDto.getTitle(), postId);
        return getPostById(postId);
    }
}
