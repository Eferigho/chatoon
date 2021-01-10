package com.chaton.web;


import com.chaton.model.post.Like;
import com.chaton.service.LikeService;
import com.chaton.service.dto.LikeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class LikeController {

    @Autowired
    LikeService likeService;

    @GetMapping("/{username}/posts/{postId}/likes")
    public List<Like> postLikes(@PathVariable Long postId) throws Exception {
        return  likeService.likes(postId);
    }

    @PostMapping("/{username}/posts/{postId}/like")
    public String createLike(@RequestBody LikeDto likeDto) throws Exception {
        likeService.createLike(likeDto.getLikeAuthor(),likeDto.getPostId(),likeDto.getChoice());
        return "";
    }

    @PutMapping("/{username}/posts/{postId}/like")
    public String updateLike(@RequestBody LikeDto likeDto) throws Exception {
        likeService.updateLike(likeDto.getLikeAuthor(),likeDto.getPostId(),likeDto.getChoice(),likeDto.getLikeId());
        return "";
    }
}
