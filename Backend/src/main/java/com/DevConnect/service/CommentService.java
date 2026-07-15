package com.DevConnect.service;

import com.DevConnect.dto.comment.CommentResponse;
import com.DevConnect.dto.comment.CreateCommentRequest;
import com.DevConnect.dto.comment.UpdateCommentRequest;
import com.DevConnect.dto.vote.VoteRequest;
import com.DevConnect.exception.InvalidRequestException;
import com.DevConnect.mapper.CommentMapper;
import com.DevConnect.model.*;
import com.DevConnect.repository.CommentRepo;
import com.DevConnect.repository.CommentVoteRepo;
import com.DevConnect.repository.PostRepo;
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
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final CommentVoteRepo commentVoteRepo;
    private final CurrentUserService currentUserService;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt",
            "updatedAt"
    );

    public CommentResponse addComment(CreateCommentRequest createCommentRequest, Integer postId){
        Comment comment = commentMapper.toEntity(createCommentRequest);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        User user =currentUserService.getCurrentUser();
        comment.setUser(user);

        Post post = postRepo.findById(postId).orElseThrow(()->new EntityNotFoundException("Post not found"));
        if(createCommentRequest.parentCommentId() != null){
            Comment commentParentComment = commentRepo.findById(createCommentRequest.parentCommentId()).orElseThrow(()->new EntityNotFoundException("Parent comment not found"));
            if(!(commentParentComment.getPost().getPostId().equals(post.getPostId()))) {
                throw new InvalidRequestException("Parent comment belongs to another post");
            }
            comment.setParentComment(commentParentComment);


        }
        comment.setPost(post);
       Comment saved= commentRepo.save(comment);
        return commentMapper.toResponse(saved);

    }

    public CommentResponse editComment(UpdateCommentRequest updateCommentRequest, Integer postId, Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new EntityNotFoundException("Comment not found"));

        if(!(comment.getPost().getPostId().equals(postId))) {
            throw new EntityNotFoundException("Post not found");
        }
        currentUserService.validateOwnership(comment.getUser().getUserId());

        commentMapper.updateEntity(updateCommentRequest,  comment);

        comment.setUpdatedAt(LocalDateTime.now());
        Comment saved=commentRepo.save(comment);
        return commentMapper.toResponse(saved);

    }

    public String deleteComment(Integer postId, Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new EntityNotFoundException("Comment not found"));
        if(!(comment.getPost().getPostId().equals(postId))) {
            throw new EntityNotFoundException("Post not found");
        }
       currentUserService.validateOwnership(comment.getUser().getUserId());
        commentRepo.delete(comment);
        return "comment has been deleted";
    }

    public CommentResponse getComment(Integer postId, Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new EntityNotFoundException("Comment not found"));
        if(!(comment.getPost().getPostId().equals(postId))) {
            throw new EntityNotFoundException("Post not found");
        }
        return commentMapper.toResponse(comment);
    }

    public String voteComment(Integer postId, Integer commentId, VoteRequest voteRequest) {
       Comment comment =commentRepo.findById(commentId).orElseThrow(()->new EntityNotFoundException("Comment not found"));

        if(!(postId.equals(comment.getPost().getPostId()))) {
            throw new InvalidRequestException("comment does not belong to this post");
        }
        User user =currentUserService.getCurrentUser();
        Optional<CommentVote> existingVote=commentVoteRepo.findByUserAndComment(user,comment);

        if(existingVote.isPresent()){
            if(existingVote.get().getVoteType().equals(voteRequest.voteType()))
            {
                commentVoteRepo.delete(existingVote.get());
                return "vote deletion success";
            }
            else
            {
                existingVote.get().setVoteType(voteRequest.voteType());
                commentVoteRepo.save(existingVote.get());
                return "vote edited successfully";

            }
        }
        CommentVote commentVote = new CommentVote();
        commentVote.setVoteType(voteRequest.voteType());
        commentVote.setUser(user);
        commentVote.setComment(comment);
        commentVoteRepo.save(commentVote);
        return "voted successfully";

    }


    public Page<CommentResponse> getAllComments(Integer postId, Integer page, Integer size, String sortBy, Sort.Direction direction) {
        postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        validateSortField(sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,sortBy));
        Page<Comment> comments=commentRepo.findByPostPostId(postId,pageable);
        return comments.map(commentMapper::toResponse);
    }
    private void validateSortField(String sortBy) {
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidRequestException("Invalid sort field");
        }
    }

}
