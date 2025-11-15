package com.kuza.kuzasokoni.common.authentication.Dto;

import lombok.Setter;
import lombok.Getter;

    @Setter
    @Getter
    public class OtpVerifyRequest {
        private String phoneNumber;
        private String otp;
    }
