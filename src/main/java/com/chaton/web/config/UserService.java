package com.chaton.web.config;

import com.chaton.exception.UsernameExist;
import com.chaton.model.user.Gender;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

   ResponseEntity<ApplicationUser> createUser(String username, String password,
                                              String email, Gender gender, String role) throws UsernameExist, Exception;

    ResponseEntity<ApplicationUser> updateUser(String username, String password, String firstName, String lastName,
                                               String email, String phoneNumber, String profileImage,
                                               String userBillBoard, String role) throws Exception;

    ResponseEntity<?> generateToken(String username, String password);
    Optional<ApplicationUser> findUserByUsername(String username) throws Exception;

    List<ApplicationUser> findAllUser();

    void deleteByUsername(String username);


}
