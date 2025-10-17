package com.kuza.kuzasokoni.domain.client.dtos.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class GuarantorCreateCommand {
        private String name;
        private String relationship;
        private String phoneNumber;
        private String address;
         private Boolean isVerified;
    }

