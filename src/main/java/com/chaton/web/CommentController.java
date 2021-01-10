package com.chaton.web;

import com.chaton.model.post.Comment;
import com.chaton.service.CommentService;
import com.chaton.service.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("{username}/posts/{postId}/comments")
    public List<Comment> allComments(@PathVariable Long postId) throws Exception {
        return commentService.postComments(postId);
    }

    @PostMapping("{username}/posts/{postId}/comment")
    public String createComment(@RequestBody CommentDto commentDto) throws Exception {
        commentService.createComment(commentDto.getCommentAuthor(),commentDto.getPostId(),commentDto.getCommentBody());
        return "";
    }

    @DeleteMapping("{username}/posts/{postId}/comments")
    public void deleteComments(@RequestBody CommentDto commentDto) throws Exception {
        commentService.deleteComment(commentDto.getCommentAuthor(),commentDto.getCommentId());
    }
}
