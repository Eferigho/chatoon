package com.chaton.service;

import com.chaton.model.post.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    ResponseEntity<Post> createPost(String postUser, String postBody)throws Exception;

    ResponseEntity<Post> updatePost(String postUser, Long postId, String postBody)throws Exception;

    List<Post> allMyPost(String postUser)throws Exception;

    void deletePost(String user, Long postId)throws  Exception;

    Post findById(Long postId) throws Exception;
}
