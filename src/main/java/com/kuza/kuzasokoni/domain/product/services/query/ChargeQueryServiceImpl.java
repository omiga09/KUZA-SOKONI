package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ChargeQueryServiceImpl implements ChargeQueryService {

    private final ChargeRepository repository;

    @Override
    public List<ChargeView> getAllCharges() {
        return repository.findAllProjected();
    }

    @Override
    public Optional<ChargeView> getChargeById(Long id) {
        return repository.findProjectedById(id);
    }
}
