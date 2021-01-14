package com.chaton.web.config;

import com.chaton.exception.UsernameExist;
import com.chaton.model.user.Gender;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

   ResponseEntity<ApplicationUser> createUser(String email, String password,
                                               Gender gender, String role, HttpServletRequest request) throws UsernameExist, Exception;

   boolean verify(String code, HttpServletRequest request)throws UnsupportedEncodingException, MessagingException;

    String resetPassword(String email, HttpServletRequest request)throws UnsupportedEncodingException, MessagingException;

   String confirmResetPassword(String resetPasswordToken, String newPassword, String confirmPassword, HttpServletRequest request)throws UnsupportedEncodingException, MessagingException;

    ResponseEntity<ApplicationUser> updateUser(String email, String password, String firstName, String lastName,
                                               String profileName, String phoneNumber, String profileImage,
                                               String userBillBoard, String role) throws Exception;

    ResponseEntity<?> generateToken(String email, String password);

    Optional<ApplicationUser> findUserByUsername(String email) throws Exception;

    List<ApplicationUser> findAllUser();

    void deleteByUsername(String email);


}
