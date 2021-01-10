package com.chaton.model.post;

import com.chaton.web.config.ApplicationUser;
import com.chaton.repository.CommentRepository;
import com.chaton.repository.PostRepository;
import com.chaton.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentTest {

    Comment comment;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        comment = new Comment();
    }

    @Test
    void createNewComment(){
        Optional<ApplicationUser> author = userRepository.findByUsername("Francis");
        Post post = postRepository.findById(9L).orElseThrow();
        comment.setAuthor(author.orElseThrow());
        comment.setPost(post);
        String myComment = "You will not believe what happen today at Lekki Toll Gate?";
        comment.setCommentBody(myComment);
        commentRepository.save(comment);

        assertNotNull(commentRepository);
    }

    @Test
    void deletePostByAuthor(){
        Optional<ApplicationUser> author = userRepository.findByUsername("Francis");
        Post post = postRepository.findById(9L).orElseThrow();
        commentRepository.deleteByAuthorAndPost(author,post);
    }
}