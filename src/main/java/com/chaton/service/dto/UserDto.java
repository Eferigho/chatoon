package com.chaton.service.dto;

import com.chaton.model.post.Image;
import com.chaton.model.user.Address;
import com.chaton.model.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private  String username;
    private String password;
    private String firstName;
    private  String lastName;
    private String email;
    private String role;
    private Gender gender;
    private  String phoneNumber;
    private  String profileImage;
    private String userBillBoard;
    private Address address;

}
