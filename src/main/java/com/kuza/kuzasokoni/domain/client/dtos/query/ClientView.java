package com.kuza.kuzasokoni.domain.client.dtos.query;

import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        VerificationStatus getIsVerified();
        LocalDateTime getCreatedAt();
        LocalDateTime getUpdatedAt();
        LocalDateTime getSubmittedAt();
}
