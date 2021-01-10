package com.chaton.repository;

import com.chaton.model.post.Post;
import com.chaton.web.config.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional

public interface PostRepository extends JpaRepository <Post, Long> {
    Optional<Post> findByAuthorAndId(Optional<ApplicationUser> author, Long id);

    List<Post> findByAuthor(Optional<ApplicationUser> author);

    void deleteByAuthor(ApplicationUser author);

    void deleteByAuthorAndId(Optional<ApplicationUser> author, Long id);
}
