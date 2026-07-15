package com.DevConnect.repository;

import com.DevConnect.model.Post;
import com.DevConnect.model.PostVote;
import com.DevConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepo extends JpaRepository<PostVote,Integer> {

    Optional<PostVote> findByUserAndPost(User user, Post post);
}
