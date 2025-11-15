package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;

public interface TenureCommandMapper {
    TenureCreateCommand toDto(Tenure tenure);
    Tenure toEntity(TenureCreateCommand cmd, Product product);
    Tenure toEntity(TenureUpdateCommand cmd, Product product);
}
