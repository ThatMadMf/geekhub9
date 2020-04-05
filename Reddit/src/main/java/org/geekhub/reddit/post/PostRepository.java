package org.geekhub.reddit.post;

import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    private static final String RESOURCE_PATH = "templates/sql/post/";
    private static final String SELECT_ALL_BY_SUBREDDIT_ID = getSql("select_subredditId.sql");
    private static final String SELECT_BY_ID = getSql("select_id.sql");
    private static final String INSERT_POST = getSql("insert.sql");
    private static final String UPDATE_POST_BY_ID = getSql("update_id.sql");
    private static final String DELETE_POST_CONTENT = getSql("delete_id.sql");

    private final BeanPropertyRowMapper<Post> postMapper;
    private final JdbcTemplate jdbcTemplate;


    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        postMapper = new BeanPropertyRowMapper<>(Post.class);
    }

    public List<Post> getPostsBySubredditId(int subredditId) {
        return jdbcTemplate.query(SELECT_ALL_BY_SUBREDDIT_ID, new Object[]{subredditId}, postMapper);
    }

    public Post getPostById(int postId) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{postId}, postMapper);
    }

    public Post createPost(Post post) {
        jdbcTemplate.update(INSERT_POST, post.getCreatorId(), post.getSubredditId(), post.getTitle(),
                post.getCreationDate(), post.getContent());
        return post;
    }

    public Post editPost(PostDto postDto, int postId) {
        jdbcTemplate.update(UPDATE_POST_BY_ID, postDto.getTitle(), postDto.getTitle(), postId);
        return getPostById(postId);
    }

    public void deletePost(int postId) {
        jdbcTemplate.update(DELETE_POST_CONTENT, postId);
    }

    private static String getSql(String fileName) {
        return ResourceReader.getSql(RESOURCE_PATH + fileName);
    }
}
