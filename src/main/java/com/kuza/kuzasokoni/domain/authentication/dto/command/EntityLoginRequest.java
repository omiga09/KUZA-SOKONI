package com.kuza.kuzasokoni.domain.authentication.dto.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityLoginRequest {
    private String email;
    private String password;
}
