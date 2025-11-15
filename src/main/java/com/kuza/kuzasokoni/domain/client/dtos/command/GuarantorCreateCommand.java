package com.kuza.kuzasokoni.domain.client.dtos.command;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class GuarantorCreateCommand {

    @NotBlank(message = "Guarantor name is required")
    private String name;

    @NotBlank(message = "Relationship is required")
    private String relationship;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^255\\d{9}$", message = "Guarantor's phone number must start with 255 and contain 12 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Verification status is required")
    private Boolean isVerified;
}

