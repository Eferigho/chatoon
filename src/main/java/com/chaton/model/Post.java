package com.chaton.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column(length = 50, nullable = false)
    private String post_title;

    @Column(length = 1000, nullable = false)
    private String post_body;

    @Column(length = 100)
    private String post_file;


    private LocalDate date_posted;

    private LocalDate date_updated;

    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    public Post(@NotNull Long id, String post_body) {
        this.id = id;
        this.post_body = post_body;
    }

    public Post(@NotNull Long id, String post_title, String post_body, LocalDate date_posted) {
        this.id = id;
        this.post_title = post_title;
        this.post_body = post_body;
        this.date_posted = date_posted;
    }

    public Post(@NotNull Long id, String post_title, String post_body, String post_file, LocalDate date_posted) {
        this.id = id;
        this.post_title = post_title;
        this.post_body = post_body;
        this.post_file = post_file;
        this.date_posted = date_posted;
    }

}
