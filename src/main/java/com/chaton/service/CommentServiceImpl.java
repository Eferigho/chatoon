package com.chaton.service;

import com.chaton.model.post.Comment;
import com.chaton.model.post.Post;
import com.chaton.web.config.ApplicationUser;
import com.chaton.repository.CommentRepository;
import com.chaton.web.config.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl  implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;
    @Override
    public ResponseEntity<Comment> createComment(String author, Long postId, String commentBody) throws Exception {
        try {
            ApplicationUser commentAuthor = userService.findUserByUsername(author).orElseThrow();
            Post postToCommentOn = postService.findById(postId);
            if (Optional.ofNullable(commentAuthor).isPresent() && Optional.ofNullable(postToCommentOn).isPresent()){
                Comment comment = new Comment();
                comment.setPost(postToCommentOn);
                comment.setAuthor(commentAuthor);
                comment.setCommentBody(commentBody);
                commentRepository.save(comment);

                return new  ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<Comment> updateComment(String author, String commentBody, Long commentId) throws Exception {
        try {
            ApplicationUser commentAuthor = userService.findUserByUsername(author).orElseThrow();
            Comment myComment = commentRepository.findById(commentId).orElseThrow();
            if (Optional.ofNullable(commentAuthor).isPresent() && Optional.ofNullable(myComment).isPresent()){
                myComment.setCommentBody(commentBody);
                commentRepository.save(myComment);

                return new  ResponseEntity<>(HttpStatus.OK);
            }

        }
        catch (Exception ex){
            ex.getMessage();
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Comment> postComments(Long postId) throws Exception {
        try {
            Post postCommentedOn = postService.findById(postId);
            return commentRepository.findByPost(postCommentedOn);
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> deleteComment(String author, Long commentId) throws Exception {
        try {
            ApplicationUser commentAuthor = userService.findUserByUsername(author).orElseThrow();
            Comment myComment = commentRepository.findById(commentId).orElseThrow();
            if (myComment.getAuthor().getUsername().equals(commentAuthor.getUsername())){
                commentRepository.deleteById(commentId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
