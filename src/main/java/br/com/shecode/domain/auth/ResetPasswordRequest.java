package br.com.shecode.domain.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
