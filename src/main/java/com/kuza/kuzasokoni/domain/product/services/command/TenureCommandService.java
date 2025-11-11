package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureUpdateCommand;

public interface TenureCommandService {
    TenureCreateCommand createTenure(TenureCreateCommand cmd);
    TenureUpdateCommand updateTenure(TenureUpdateCommand cmd);
    void deleteTenure(Long id);
}
