package com.kuza.kuzasokoni.domain.authentication.dto.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String newPassword;

}
