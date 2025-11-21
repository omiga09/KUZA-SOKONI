package com.kuza.kuzasokoni.domain.authentication.dto.command;


import com.kuza.kuzasokoni.common.utils.EntityType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CustomerCreateCommand {
    private String firstName;
    private String secondName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private List<EntityType> entityTypes;
    private String pin;
}