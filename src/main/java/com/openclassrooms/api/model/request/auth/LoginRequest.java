package com.openclassrooms.api.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * LoginRequest DTO
 */
@Builder
@Data
public class LoginRequest {

    @Email
    private String email;

    @NotBlank
    private String password;
}
