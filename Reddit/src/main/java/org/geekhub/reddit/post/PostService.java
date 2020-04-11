package org.geekhub.reddit.post;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.exception.NoRightsException;
import org.geekhub.reddit.user.UserRepository;
import org.geekhub.reddit.vote.PostVote;
import org.geekhub.reddit.vote.Vote;
import org.geekhub.reddit.vote.VoteApplicable;
import org.geekhub.reddit.vote.VoteService;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final VoteService voteService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostService(VoteService voteService, UserRepository userRepository, PostRepository postRepository) {
        this.voteService = voteService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Post> getAllPostBySubredditId(int subredditId) {
        return postRepository.getPostsBySubredditId(subredditId);
    }

    public Post getPostById(int postId) {
        return postRepository.getPostById(postId);
    }

    public Post addPost(PostDto postDto, String authorLogin, int id) {
        Post post = new Post(postDto, userRepository.getUserByLogin(authorLogin).getId(), id);
        if (postRepository.createPost(post) == 1) {
            return post;
        }
        throw new DataBaseRowException("Failed to add post");
    }

    public Vote submitVote(boolean voteValue, String authorLogin, int id) {
        Vote vote = new PostVote(voteValue, userRepository.getUserByLogin(authorLogin).getId(), id);
        return voteService.submitVote(vote);
    }

    public List<Vote> getAllVotesByPostId(int id) {
        return voteService.getAllVotesByAppliedId(id, VoteApplicable.POST);
    }


    public Post editPost(PostDto postDto, int postId, String editorLogin) {
        Post editedPost = getPostById(postId);
        checkAuthority(editedPost, editorLogin);

        if (postRepository.editPost(postDto, postId) == 1) {
            return editedPost;
        }
        throw new DataBaseRowException("Failed to edit post");
    }

    public int getVotesCount(int id) {
        return getAllVotesByPostId(id).stream()
                .mapToInt(vote -> vote.isVote() ? 1 : -1).sum();
    }

    public void deletePostContent(int postId, String editorLogin) {
        Post editedPost = getPostById(postId);
        checkAuthority(editedPost, editorLogin);

        postRepository.deletePost(postId);
    }

    private void checkAuthority(Post editedPost, String editorLogin) {
        if (editedPost.getCreatorId() != userRepository.getUserByLogin(editorLogin).getId()) {
            throw new NoRightsException("You have no rights to edit this post");
        }
    }

    public List<Post> getPopularPosts() {
        return postRepository.getAllPosts().stream()
                .sorted(Comparator.comparingInt(p -> getVotesCount(p.getId())))
                .limit(50)
                .collect(Collectors.toList());
    }
}
