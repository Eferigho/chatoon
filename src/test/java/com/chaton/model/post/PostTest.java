package com.chaton.model.post;

import com.chaton.web.config.ApplicationUser;
import com.chaton.repository.CommentRepository;
import com.chaton.repository.LikeRepository;
import com.chaton.repository.PostRepository;
import com.chaton.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql(scripts = {"db/insert_data.sql"})
class PostTest {
    Logger logger = Logger.getLogger(PostTest.class.getName());

    Post post;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikeRepository likeRepository;

    @BeforeEach
    void setUp() {
        post = new Post();
    }

    @Test
    void createNewPost(){
        Optional<ApplicationUser> postUser = userRepository.findByUsername("Efe");
        post.setPostBody("England is one of two countries");
        post.setAuthor(postUser.orElseThrow());
        postRepository.save(post);
    }
    @Test
    void addImageToPost(){

        Optional<ApplicationUser> postUser = userRepository.findByUsername("Francis");
        Image image = new Image();
        image.setImageUrl("sgwguqhdwdg");
        image.setUser(postUser.orElseThrow());
        List<Image> myImages = new ArrayList<>();
        myImages.add(image);
//        post.setPostImages(myImages);
    }

    @Test
    void updatePost(){
        Optional<ApplicationUser> postUser = userRepository.findByUsername("Francis");
        List<Post> allMyPost = postRepository.findByAuthor(postUser);
        Post updatePost = allMyPost.get(0);
        updatePost.setPostBody("We really like Falz the bad guy");
        postRepository.save(updatePost);
        assertThat("We really like Falz the bad guy").isEqualTo(updatePost.getPostBody());
    }

    @Test
    void findPostByAuthorAndId(){
        Optional<ApplicationUser> postUser = userRepository.findByUsername("Francis");
        String whatIPosted = "Falz na my guy";
        Optional<Post> post = postRepository.findByAuthorAndId(postUser, 1L);
        assertTrue(post.isPresent());
        assertThat(post.get().getPostBody()).isEqualTo(whatIPosted);
    }

    @Test
    void allAuthorPost(){
        Optional<ApplicationUser> postUser = userRepository.findByUsername("Francis");
        List<Post> allMyPost = postRepository.findByAuthor(postUser);
        logger.info(postUser.get().getUsername() +" total post : "+ String.valueOf(allMyPost.size()));
        assertThat(1).isEqualTo(allMyPost.size());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deleteAllAuthorPost(){

        ApplicationUser postUser = userRepository.findByUsername("Francis").orElseThrow();
        postRepository.deleteByAuthorAndId(Optional.ofNullable(postUser),6L);
    }

    @Test
    void deletePostByAuthorAndId(){
        Long id =  17L;
        Optional<ApplicationUser> postUser = userRepository.findByUsername("Efe");
        logger.info(postUser.get().getUsername());
//        Post deletedPost = postRepository.findById(8L).orElseThrow();
        postRepository.deleteById(id);
        assertTrue(postRepository.findById(17L).isEmpty());

//        assertThat(deletedPost).isEqualTo(postRepository.findById(12L));
    }

    @Test
    void findAllPostComment(){
        Post post = postRepository.findById(6L).orElseThrow();
        List<Comment> allComment = commentRepository.findByPost(post);
        assertThat(2).isEqualTo(allComment.size());
    }

    @Test
    void findAllPostLikes(){
        Post post = postRepository.findById(6L).orElseThrow();
        List<Like> allLikes = likeRepository.findByPost(post);
        assertThat(0).isEqualTo(allLikes.size());
    }
}