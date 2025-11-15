package com.kuza.kuzasokoni.domain.client.dtos.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.entities.Guarantor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateCommand {
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Second name is required")
    private String secondName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^255\\d{9}$", message = "Phone number must start with 255 and contain 12 digits")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Address is required")
    private String address;

    private List<Guarantor> guarantors;

    private List<EntityType> entityTypes;
}
