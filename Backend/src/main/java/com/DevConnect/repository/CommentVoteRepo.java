package com.DevConnect.repository;

import com.DevConnect.model.Comment;
import com.DevConnect.model.CommentVote;
import com.DevConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentVoteRepo extends JpaRepository<CommentVote,Integer> {

    Optional<CommentVote> findByUserAndComment(User user, Comment comment);
}
