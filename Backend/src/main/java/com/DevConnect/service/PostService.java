package com.DevConnect.service;


import com.DevConnect.dto.post.CreatePostRequest;
import com.DevConnect.dto.post.PostResponse;
import com.DevConnect.dto.post.UpdatePostRequest;
import com.DevConnect.dto.vote.VoteRequest;
import com.DevConnect.exception.InvalidRequestException;
import com.DevConnect.mapper.PostMapper;
import com.DevConnect.model.Post;
import com.DevConnect.model.PostVote;
import com.DevConnect.model.User;
import com.DevConnect.repository.PostRepo;
import com.DevConnect.repository.PostVoteRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final PostVoteRepo postVoteRepo;
    private final CurrentUserService currentUserService;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of(
                    "createdAt",
                    "updatedAt",
                    "title",
                    "content"
            );



    public PostResponse createPost(CreatePostRequest postRequest) {

        Post post = postMapper.toEntity(postRequest);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        User user = currentUserService.getCurrentUser();
        post.setUser(user);
        postRepo.save(post);
        return postMapper.toResponse(post);

    }

    public PostResponse updatePost(UpdatePostRequest updatePostRequest, Integer id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post Not Found")
        );
        currentUserService.validateOwnership(post.getUser().getUserId());
        postMapper.updateEntity(updatePostRequest, post);
        post.setUpdatedAt(LocalDateTime.now());
        postRepo.save(post);
        return postMapper.toResponse(post);
    }

    public String deletePost(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post Not Found")
        );
        currentUserService.validateOwnership(post.getUser().getUserId());
        postRepo.delete(post);
        return "successfully deleted the post";
    }


    public String votePost(Integer id, VoteRequest voteRequest) {
        Post post = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Post Not Found"));
        User user = currentUserService.getCurrentUser();
        Optional<PostVote> existingVote = postVoteRepo.findByUserAndPost(user, post);
        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType().equals(voteRequest.voteType())) {
                postVoteRepo.delete(existingVote.get());
                return "successfully removed the vote";
            } else {
                existingVote.get().setVoteType(voteRequest.voteType());
                postVoteRepo.save(existingVote.get());
                return "successfully changed the vote";
            }
        }
        PostVote postVote = new PostVote();
        postVote.setVoteType(voteRequest.voteType());
        postVote.setUser(user);
        postVote.setPost(post);
        postVoteRepo.save(postVote);
        return "successfully voted";


    }
    public PostResponse getPost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        return postMapper.toResponse(post);
    }
    public Page<PostResponse> getAllPosts(int page, int size, String sortBy, Sort.Direction direction) {
        validateSortField(sortBy);
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,sortBy));
        Page<Post> allPosts = postRepo.findAll(pageable);
        return allPosts.map(postMapper::toResponse);
    }

    public Page<PostResponse> searchPosts(String keyword, int page, int size, String sortBy, Sort.Direction direction) {
       validateSortField(sortBy);
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,sortBy));
        keyword=keyword.trim();
        if (keyword.isEmpty()) {
            throw new InvalidRequestException("keyword is empty");
        }
        Page<Post> allPosts= postRepo.searchPosts(keyword,pageable);
        return allPosts.map(postMapper::toResponse);
    }
    private void validateSortField(String sortBy) {
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidRequestException("Invalid sort field");
        }
    }
}
