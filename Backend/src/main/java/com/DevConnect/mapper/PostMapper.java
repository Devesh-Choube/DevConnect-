package com.DevConnect.mapper;


import com.DevConnect.dto.post.CreatePostRequest;
import com.DevConnect.dto.post.PostResponse;
import com.DevConnect.dto.post.UpdatePostRequest;
import com.DevConnect.model.Post;
import com.DevConnect.model.VoteType;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(CreatePostRequest createPostRequest) {
        Post post = new Post();
        post.setTitle(createPostRequest.title());
        post.setContent(createPostRequest.content());
        return post;
    }
   public PostResponse toResponse(Post post) {
        Long upVotes=post.getPostVotes().stream().filter(v->v.getVoteType()== VoteType.UPVOTE).count();
        Long downVotes=post.getPostVotes().stream().filter(v->v.getVoteType()==VoteType.DOWNVOTE).count();
        return new PostResponse(post.getPostId(),post.getUser().getUsername(),post.getTitle(), post.getContent(), post.getCreatedAt(),post.getUpdatedAt(),upVotes,downVotes,(long) post.getComments().size());
   }
   public void updateEntity(UpdatePostRequest postRequest, Post post) {
        post.setTitle(postRequest.title());
        post.setContent(postRequest.content());
   }
}
