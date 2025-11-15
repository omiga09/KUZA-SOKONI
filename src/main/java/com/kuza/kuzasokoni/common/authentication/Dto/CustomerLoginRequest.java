package com.kuza.kuzasokoni.common.authentication.Dto;

import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class CustomerLoginRequest {
        private String phoneNumber;
        private String pin;
    }

