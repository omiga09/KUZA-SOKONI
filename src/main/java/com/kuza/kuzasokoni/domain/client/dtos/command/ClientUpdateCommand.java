package com.kuza.kuzasokoni.domain.client.dtos.command;


import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateCommand {
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private ClientStatus status;
    private VerificationStatus isVerified;

    private DocumentationCreateCommand documentation;
}