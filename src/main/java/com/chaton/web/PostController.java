package com.chaton.web;

import com.chaton.model.post.Post;
import com.chaton.service.PostService;
import com.chaton.service.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/{username}/post")
    public  ResponseEntity<Post> newPost(@RequestBody PostDto postDto) throws Exception {
        return postService.createPost(postDto.getAuthor(),postDto.getPostBody());
    }

    @GetMapping("/{username}/posts")
    public List<Post> allPost(@PathVariable String username) throws Exception {
        return postService.allMyPost(username);
    }

    @PutMapping("/{username}/posts")
    public ResponseEntity<Post> updatePost(@RequestBody PostDto postDto) throws Exception {
        return postService.updatePost(postDto.getAuthor(),postDto.getPostId(), postDto.getPostBody());
    }
    @DeleteMapping("/{username}/posts")
    public void deletePost(@RequestBody PostDto postDto) throws Exception {
        postService.deletePost(postDto.getAuthor(),postDto.getPostId());
    }
}
