package com.DevConnect.repository;

import com.DevConnect.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query("SELECT p from Post p WHERE "+
    "LOWER(p.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
    "LOWER(p.content) LIKE LOWER(CONCAT('%',:keyword,'%'))" )
    Page<Post> searchPosts( @Param("keyword") String keyword,Pageable pageable);
}
