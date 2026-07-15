package com.DevConnect.mapper;


import com.DevConnect.dto.comment.CommentResponse;
import com.DevConnect.dto.comment.CreateCommentRequest;
import com.DevConnect.dto.comment.UpdateCommentRequest;
import com.DevConnect.model.Comment;
import com.DevConnect.model.VoteType;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest createCommentRequest) {
        Comment comment = new Comment();
        comment.setContent(createCommentRequest.content());
        return comment;
    }
    public void updateEntity(UpdateCommentRequest updateCommentRequest,Comment comment) {
        comment.setContent(updateCommentRequest.content());
    }
    public CommentResponse toResponse(Comment comment) {
        Long upVotes = comment.getCommentVotes().stream().filter(v->v.getVoteType()== VoteType.UPVOTE).count();
        Long downVotes = comment.getCommentVotes().stream().filter(v->v.getVoteType()==VoteType.DOWNVOTE).count();
        return new CommentResponse(comment.getCommentId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                upVotes,downVotes,
                (long)comment.getReplies().size());
    }


}
