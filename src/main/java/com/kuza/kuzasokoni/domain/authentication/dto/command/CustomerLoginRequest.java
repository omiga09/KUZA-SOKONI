package com.kuza.kuzasokoni.domain.authentication.dto.command;

import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class CustomerLoginRequest {
        private String phoneNumber;
        private String pin;
    }

