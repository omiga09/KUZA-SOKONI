package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.Product;

import java.util.List;

public interface ProductCommandMapper {
     Product toEntity(ProductCreateCommand cmd);
    void updateEntity(ProductUpdateCommand cmd, Product product);
}
