package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.DocumentationCreateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Documentation;
import org.springframework.stereotype.Component;

@Component
public class DocumentationCommandMapperImpl implements DocumentationCommandMapper {

    @Override
    public Documentation toEntity(DocumentationCreateCommand cmd) {
        if (cmd == null) return null;

        return Documentation.builder()
                .nidaNumber(cmd.getNidaNumber())
                .baruaReference(cmd.getBaruaReference())
                .kitambulishoType(cmd.getKitambulishoType())
                .kitambulishoNumber(cmd.getKitambulishoNumber())
                .build();
    }
}
