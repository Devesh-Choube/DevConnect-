package com.DevConnect.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id","user_id"})})
@Getter
@Setter
public class PostVote{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
