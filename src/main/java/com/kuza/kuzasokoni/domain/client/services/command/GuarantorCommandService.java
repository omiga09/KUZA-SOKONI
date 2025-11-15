package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorUpdateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;

public interface GuarantorCommandService {
    ClientView updateGuarantors(GuarantorUpdateCommand cmd);
}
