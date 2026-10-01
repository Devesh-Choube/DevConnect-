package com.DevConnect.repository;

import com.DevConnect.model.Comment;
import com.DevConnect.model.CommentVote;
import com.DevConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentVoteRepo extends JpaRepository<CommentVote,Integer> {


    @Modifying
    @Query("DELETE FROM CommentVote cv WHERE cv.user.userId = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
    Optional<CommentVote> findByUserAndComment(User user, Comment comment);
}
