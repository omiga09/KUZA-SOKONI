package com.kuza.kuzasokoni.domain.authentication.dto.command;

import lombok.Setter;
import lombok.Getter;

    @Setter
    @Getter
    public class OtpVerifyRequest {
        private String email;
        private String phoneNumber;
        private String otp;
    }
