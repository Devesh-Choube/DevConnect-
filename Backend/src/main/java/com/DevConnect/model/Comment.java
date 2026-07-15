package com.DevConnect.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    Comment parentComment;

    @OneToMany(mappedBy = "parentComment",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> replies=new ArrayList<>();

    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CommentVote> commentVotes=new HashSet<>();
}
