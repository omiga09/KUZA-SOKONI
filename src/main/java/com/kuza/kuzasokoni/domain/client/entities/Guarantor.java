package com.kuza.kuzasokoni.domain.client.entities;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guarantor {
    private String name;
    private String relationship;
    private String phoneNumber;
    private String address;
    private Boolean isVerified;
}
