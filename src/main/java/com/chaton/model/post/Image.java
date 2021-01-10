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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private ApplicationUser user;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private  Post post;

    private  String imageUrl;

}
