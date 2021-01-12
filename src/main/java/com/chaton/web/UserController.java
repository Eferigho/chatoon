package com.chaton.web;


import com.chaton.exception.UsernameExist;

import com.chaton.service.dto.UserDto;
import com.chaton.web.config.ApplicationUser;
import com.chaton.web.config.LoginDTO;
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
        return userService.generateToken(loginDTO.getUsername(),loginDTO.getPassword());
    }

    @PreAuthorize("hasAnyRole('BUSINESS_USER, NORMAL_USER')")
    @GetMapping("/chaton")
    public List<ApplicationUser> allUser(){

        return userService.findAllUser();
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL_USER')")
    public Optional<ApplicationUser> findUserByUserName(@PathVariable String username) throws Exception {
        return  userService.findUserByUsername(username);

    }


    @PostMapping("/register")
    public String registerUser(@Validated @RequestBody UserDto userDto, HttpServletRequest request) throws Exception, UsernameExist {
       userService.createUser(userDto.getUsername(),userDto.getPassword(),
               userDto.getEmail(),userDto.getGender(), userDto.getRole(), request);
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

    @PutMapping("/{username}")
    public String updateUser(@Validated @RequestBody UserDto userDto) throws Exception {
        userService.updateUser(userDto.getUsername(),userDto.getPassword(),
               userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),userDto.getPhoneNumber(),
                userDto.getProfileImage(),
                userDto.getUserBillBoard(),userDto.getRole());
        return "";
    }

    @DeleteMapping("/{username}")
    ResponseEntity<?> deleteUser(@PathVariable String username){
        userService.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

}
