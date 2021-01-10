package com.chaton.repository;

import com.chaton.model.post.Comment;
import com.chaton.model.post.Post;
import com.chaton.web.config.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    void deleteByAuthorAndPost(Optional<ApplicationUser> author, Post post);
}
