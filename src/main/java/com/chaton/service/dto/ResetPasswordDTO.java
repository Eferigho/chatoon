package com.chaton.service.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String email;
    private String newPassword;
    private String confirmPassword;
}
