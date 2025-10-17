package com.kuza.kuzasokoni.domain.client.dtos.command;

import com.kuza.kuzasokoni.domain.loan.enums.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentationCreateCommand {
    private String nidaNumber;
    private String baruaReference;
    private String kitambulishoType;
    private String kitambulishoNumber;
    private DocumentStatus status;
    private Boolean isVerified;
}
