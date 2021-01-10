package com.chaton.model.post;

import com.chaton.web.config.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private ApplicationUser author;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Post post;

    @Enumerated(EnumType.STRING)
    private Choice likeValue;

}
