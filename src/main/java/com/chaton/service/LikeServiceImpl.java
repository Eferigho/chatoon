package com.chaton.service;

import com.chaton.model.post.Choice;
import com.chaton.model.post.Like;
import com.chaton.model.post.Post;
import com.chaton.web.config.ApplicationUser;
import com.chaton.repository.LikeRepository;
import com.chaton.web.config.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class LikeServiceImpl implements LikeService{
    Logger logger = Logger.getLogger(LikeServiceImpl.class.getName());

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Override
    public ResponseEntity<Like> createLike(String likeAuthor, Long postId, Choice choice) throws  Exception {
        try {
            Optional<ApplicationUser> author = userService.findUserByUsername(likeAuthor);
            Optional<Post> postToLike = Optional.ofNullable(postService.findById(postId));
            if (author.isPresent() && postToLike.isPresent()){
                Like like = new Like();
                like.setAuthor(author.orElseThrow());
                like.setPost(postToLike.orElseThrow());
                like.setLikeValue(choice);
                likeRepository.save(like);
                logger.info(author.get().getUsername()+" like "+
                        postToLike.get().getAuthor().getUsername() +" post ");
                return new ResponseEntity<>(HttpStatus.CREATED);
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

    @Override
    public ResponseEntity<Like> updateLike(String likeAuthor, Long postId, Choice choice, Long likeId) throws Exception {
        try {
            Optional<ApplicationUser> author = userService.findUserByUsername(likeAuthor);
            Optional<Post> postToLike = Optional.ofNullable(postService.findById(postId));
            Optional<Like> like = likeRepository.findById(likeId);
            if (author.isPresent() && postToLike.isPresent() && like.isPresent()){
                like.get().setLikeValue(choice);
                likeRepository.save(like.orElseThrow());
                logger.info(author.get().getUsername()+" just updated his/her like status ");
                return  new ResponseEntity<>(HttpStatus.CREATED);
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

    @Override
    public List<Like> likes(Long postId) throws Exception {
        Optional<Post> post = Optional.ofNullable(postService.findById(postId));
        if (post.isPresent()){
            return likeRepository.findByPost(post.orElseThrow());
        }
        else {
            return null;
        }
    }
}
