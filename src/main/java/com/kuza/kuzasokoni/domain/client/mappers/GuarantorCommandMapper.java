package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorCreateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Guarantor;

public interface GuarantorCommandMapper {
    Guarantor toEntity(GuarantorCreateCommand cmd);
}
