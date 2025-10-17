package com.kuza.kuzasokoni.domain.client.mappers;


import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorCreateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Guarantor;
import org.springframework.stereotype.Component;

@Component
public class GuarantorCommandMapperImpl implements GuarantorCommandMapper {

    @Override
    public Guarantor toEntity(GuarantorCreateCommand cmd) {
        return Guarantor.builder()
                .name(cmd.getName())
                .relationship(cmd.getRelationship())
                .phoneNumber(cmd.getPhoneNumber())
                .address(cmd.getAddress())
                .isVerified(cmd.getIsVerified())
                .build();
    }
}