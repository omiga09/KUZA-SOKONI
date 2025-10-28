package com.kuza.kuzasokoni.domain.product.dtos.command;

import com.kuza.kuzasokoni.common.utils.EntityType;



public record ImageCreateCommand(
     String name,
     String path,
     EntityType entityType,
     Long clientI
){}
