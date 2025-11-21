package com.kuza.kuzasokoni.domain.authentication.dto.command;

import com.kuza.kuzasokoni.common.enums.UserType;
import com.kuza.kuzasokoni.common.utils.EntityType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RegisterRequest {

    private String username;

    private String firstName;
    private String secondName;
    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private List<UserType> userType;

    private List<EntityType> entityType;
}
