package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorUpdateCommand;

public interface GuarantorCommandService {
    void updateGuarantors(GuarantorUpdateCommand cmd);
}
