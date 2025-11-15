package com.kuza.kuzasokoni.common.authentication.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String phoneNumber;
    private String password;
}

