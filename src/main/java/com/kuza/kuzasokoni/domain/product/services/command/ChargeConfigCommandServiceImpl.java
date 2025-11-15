package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;
import com.kuza.kuzasokoni.domain.product.mappers.ChargeConfigCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ChargesConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargeConfigCommandServiceImpl implements ChargeConfigCommandService {

    private final ChargesConfigRepository repository;
    private final ChargeConfigCommandMapper mapper;

    @Override
    public ChargesConfigView createConfig(ChargesConfigCreateCommand cmd) {
        ChargesConfig config = mapper.toEntity(cmd);
        ChargesConfig saved = repository.save(config);
        return repository.findProjectedById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Projection not found after save"));
    }

    @Override
    public ChargesConfigView updateConfig(ChargesConfigUpdateCommand cmd) {
        ChargesConfig config = repository.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Config not found"));
        mapper.updateEntity(cmd, config);
        ChargesConfig updated = repository.save(config);
        return repository.findProjectedById(updated.getId())
                .orElseThrow(() -> new RuntimeException("Projection not found after update"));
    }
}

