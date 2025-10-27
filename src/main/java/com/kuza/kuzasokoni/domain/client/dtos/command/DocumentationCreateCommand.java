package com.kuza.kuzasokoni.domain.client.dtos.command;

import com.kuza.kuzasokoni.domain.loan.enums.DocumentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentationCreateCommand {

    @NotBlank(message = "NIDA number is required")
    @Size(min = 10, max = 20, message = "NIDA number must be between 10 and 20 characters")
    private String nidaNumber;

    @NotBlank(message = "Kitambulisho type is required")
    private String kitambulishoType;

    @NotBlank(message = "Kitambulisho file name is required")
    private String kitambulishoFileName;

    @NotBlank(message = "Barua file name is required")
    private String baruaFileName;
}

