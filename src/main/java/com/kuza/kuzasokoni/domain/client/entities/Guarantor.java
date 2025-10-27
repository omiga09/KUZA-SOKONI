package com.kuza.kuzasokoni.domain.client.entities;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guarantor {
    @NotBlank
    private String name;

    @NotBlank
    private String relationship;

    @NotBlank
    @Pattern(regexp = "^255\\d{9}$", message = "Guarantor's phone number must start with 255 and contain 12 digits.")
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotNull
    private Boolean isVerified;

}
