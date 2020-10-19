package com.chaton.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    @Column(length = 200)
    private String comment_body;

    private LocalDate comment_date;

    private  LocalDate date_updated;

    public Comment(Long id, Post post, String comment_body){
        this.id = id;
        this.post = post;
        this.comment_body = comment_body;
    }

    public Comment(Long id, Post post, String comment_body, User author){
        this.id = id;
        this.post = post;
        this.comment_body = comment_body;
        this.author = author;
    }
}
