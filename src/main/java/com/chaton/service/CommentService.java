package com.chaton.service;

import com.chaton.model.post.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    ResponseEntity<Comment> createComment(String author, Long postId, String commentBody)throws Exception;

    ResponseEntity<Comment> updateComment(String author, String commentBody, Long commentId)throws Exception;

    List<Comment> postComments(Long postId)throws Exception;

    ResponseEntity<?> deleteComment(String author,  Long commentId) throws Exception;

}
