package com.kuza.kuzasokoni.domain.client.dtos.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClientView {
        Long getId();
        String getFirstName();
        String getSecondName();
        String getLastName();
        String getPhoneNumber();
        String getEmail();
        LocalDate getDateOfBirth();
        String getGender();
        String getAddress();
        ClientStatus getStatus();
        List<EntityType> getEntityTypes();
        VerificationStatus getIsVerified();
        LocalDateTime getCreatedAt();
        LocalDateTime getUpdatedAt();
        LocalDateTime getSubmittedAt();
}
