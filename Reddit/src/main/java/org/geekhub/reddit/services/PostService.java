package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.Vote;
import org.geekhub.reddit.db.repositories.PostRepository;
import org.geekhub.reddit.db.repositories.UserRepository;
import org.geekhub.reddit.dtos.PostDto;
import org.geekhub.reddit.dtos.VoteDto;
import org.geekhub.reddit.exception.NoRightsException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return postRepository.createPost(post);
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

        return postRepository.editPost(postDto, postId);
    }

    public int getVotesCount(int id) {
        return getAllVotesByPostId(id).stream()
                .mapToInt(vote -> vote.isVote() ? 1 : -1).sum();
    }
}
