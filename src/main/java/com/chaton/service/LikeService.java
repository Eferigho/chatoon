package com.chaton.service;

import com.chaton.model.post.Choice;
import com.chaton.model.post.Like;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LikeService {
    ResponseEntity<Like> createLike(String likeAuthor, Long postId, Choice choice) throws Exception;

    ResponseEntity<Like> updateLike(String likeAuthor, Long postId,Choice choice, Long likeId) throws Exception;

    List<Like> likes(Long postId) throws Exception;
}
