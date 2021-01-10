package com.chaton.repository;

import com.chaton.model.post.Like;
import com.chaton.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository <Like, Long> {

    List<Like> findByPost(Post post);
}
