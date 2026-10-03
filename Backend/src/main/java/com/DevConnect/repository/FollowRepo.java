package com.DevConnect.repository;

import com.DevConnect.model.Follow;
import com.DevConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Integer> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollowing(User user);

    List<Follow> findByFollower(User user);

    long countByFollowing(User user);

    long countByFollower(User user);

    @Modifying
    @Query("""
        DELETE FROM Follow f
        WHERE f.follower.userId = :userId
           OR f.following.userId = :userId
    """)
    void deleteAllByUserId(@Param("userId") Integer userId);
}