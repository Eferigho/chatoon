package com.chaton.service;

import com.chaton.model.post.Post;
import com.chaton.web.config.ApplicationUser;
import com.chaton.repository.PostRepository;
import com.chaton.web.config.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service

public class PostServiceImpl implements  PostService{


    Logger logger = Logger.getLogger(PostServiceImpl.class.getName());
    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepository;

    @Override
    public ResponseEntity<Post> createPost(String postAuthor, String postBody)throws Exception {
        try {
            Optional<ApplicationUser> author = Optional.ofNullable(userService.findUserByUsername(postAuthor).orElseThrow());
            if (author.isPresent()){
                Post post = new Post();
                post.setAuthor(author.orElseThrow());
                post.setPostBody(postBody);
                postRepository.save(post);
                logger.info(post.getAuthor().getUsername()+ " created a new post @ "+ post.getTimePosted());
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
    public ResponseEntity<Post> updatePost(String postAuthor, Long postId, String postBody) throws Exception {
       try {
           ApplicationUser author = userService.findUserByUsername(postAuthor).orElseThrow();
           if (Optional.ofNullable(author).isPresent()){
               List<Post> myPosts = postRepository.findByAuthor(Optional.of(author));
               myPosts.forEach(post -> {
                   if (post.getId().equals(postId)){
                       post.setPostBody(postBody);
                       postRepository.save(post);
                       logger.info(author.getUsername() + " updated his/her post at "+ post.getTimeUpdated());
                   }
               });
               return new ResponseEntity<>(HttpStatus.OK);
           }
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }catch (Exception ex){
           ex.getMessage();
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Override
    public List<Post> allMyPost(String postAuthor) throws Exception {
        ApplicationUser author = userService.findUserByUsername(postAuthor).orElseThrow();
        if (Optional.ofNullable(author).isPresent()){
            return postRepository.findByAuthor(Optional.of(author));
        }
        return null;
    }

    @Override
    public void deletePost(String user, Long postId) throws Exception {
        ApplicationUser author = userService.findUserByUsername(user).orElseThrow();
        if (Optional.ofNullable(author).isPresent()){

            List<Post> myPosts = postRepository.findByAuthor(Optional.of(author));
            myPosts.forEach(post -> {
                if (post.getId().equals(postId)){
                    logger.info(author.getUsername() +" just deleted his/her post @ "+ post.getTimeUpdated());
                    postRepository.deleteByAuthorAndId(Optional.of(author),postId);
                }
            });
            new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Post findById(Long postId) throws Exception {
        return postRepository.findById(postId).orElseThrow();
    }
}
