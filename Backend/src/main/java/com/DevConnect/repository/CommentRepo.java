package com.DevConnect.repository;

import com.DevConnect.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
Page<Comment> findByPostPostId(Integer postId, Pageable pageable);
}
