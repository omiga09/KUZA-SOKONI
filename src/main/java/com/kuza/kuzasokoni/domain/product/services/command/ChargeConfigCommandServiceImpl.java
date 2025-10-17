package com.kuza.kuzasokoni.domain.product.services.command;

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
    public ChargesConfig createConfig(ChargesConfigCreateCommand cmd) {
        ChargesConfig config = mapper.toEntity(cmd);
        return repository.save(config);
    }

    @Override
    public ChargesConfig updateConfig(ChargesConfigUpdateCommand cmd) {
        ChargesConfig config = repository.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Config not found"));
        mapper.updateEntity(cmd, config);
        return repository.save(config);
    }
}
