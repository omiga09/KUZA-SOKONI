package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorCreateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Guarantor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GuarantorCommandMapperImpl implements GuarantorCommandMapper {

    @Override
    public Guarantor toEntity(GuarantorCreateCommand cmd) {
        if (cmd == null) {
            throw new IllegalArgumentException("GuarantorCreateCommand cannot be null");
        }

        if (!StringUtils.hasText(cmd.getName()) ||
                !StringUtils.hasText(cmd.getRelationship()) ||
                !StringUtils.hasText(cmd.getPhoneNumber()) ||
                !StringUtils.hasText(cmd.getAddress()) ||
                cmd.getIsVerified() == null) {
            throw new IllegalArgumentException("Guarantor fields must not be null or blank");
        }

        return Guarantor.builder()
                .name(cmd.getName().trim())
                .relationship(cmd.getRelationship().trim())
                .phoneNumber(cmd.getPhoneNumber().trim())
                .address(cmd.getAddress().trim())
                .isVerified(cmd.getIsVerified())
                .build();
    }
}
