package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.repositories.ChargesConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChargesConfigQueryServiceImpl implements ChargesConfigQueryService {

    private final ChargesConfigRepository repository;

    @Override
    public List<ChargesConfigView> getAllConfigs() {
        return repository.findAllBy();
    }


    @Override
    public Optional<ChargesConfigView> getConfigById(Long id) {
        return repository.findProjectedById(id);
    }
}
