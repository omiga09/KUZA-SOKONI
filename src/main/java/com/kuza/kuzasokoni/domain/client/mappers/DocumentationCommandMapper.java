package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.DocumentationCreateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Documentation;

public interface DocumentationCommandMapper {
    Documentation toEntity(DocumentationCreateCommand cmd);
}
