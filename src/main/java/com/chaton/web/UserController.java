package com.chaton.web;


import com.chaton.exception.UsernameExist;

import com.chaton.service.dto.ResetPasswordDTO;
import com.chaton.service.dto.UserDTO;
import com.chaton.web.config.ApplicationUser;
import com.chaton.service.dto.LoginDTO;
import com.chaton.web.config.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/auth/login",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) throws Exception {
        return userService.generateToken(loginDTO.getEmail(),loginDTO.getPassword());
    }

    @PreAuthorize("hasAnyRole('BUSINESS_USER, NORMAL_USER')")
    @GetMapping("/chaton")
    public List<ApplicationUser> allUser(){

        return userService.findAllUser();
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL_USER')")
    public Optional<ApplicationUser> findUserByUserName(@PathVariable String email) throws Exception {
        return  userService.findUserByUsername(email);

    }


    @PostMapping("/register")
    public String registerUser(@Validated @RequestBody UserDTO userDto, HttpServletRequest request) throws Exception, UsernameExist {
       userService.createUser(userDto.getEmail(),userDto.getPassword(),
               userDto.getGender(), userDto.getRole(), request);
       return "";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        if (userService.verify(code, request)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return userService.resetPassword(resetPasswordDTO.getEmail(),request);
    }

    @GetMapping("/confirmPassword")
    public String confirmResetPassword(@Param("token") String token, @RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return userService.confirmResetPassword( token, resetPasswordDTO.getNewPassword(), resetPasswordDTO.getConfirmPassword(),request);


    }

    @PutMapping("/{email}")
    public String updateUser(@Validated @RequestBody UserDTO userDto) throws Exception {
        userService.updateUser(userDto.getEmail(),userDto.getPassword(),
               userDto.getFirstName(), userDto.getLastName(), userDto.getProfileName(),userDto.getPhoneNumber(),
                userDto.getProfileImage(),
                userDto.getUserBillBoard(),userDto.getRole());
        return "";
    }

    @DeleteMapping("/{email}")
    ResponseEntity<?> deleteUser(@PathVariable String email){
        userService.deleteByUsername(email);
        return ResponseEntity.ok().build();
    }

}
