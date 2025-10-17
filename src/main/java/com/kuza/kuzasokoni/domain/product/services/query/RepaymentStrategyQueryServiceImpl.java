package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.RepaymentStrategyView;
import com.kuza.kuzasokoni.domain.product.repositories.RepaymentStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepaymentStrategyQueryServiceImpl implements RepaymentStrategyQueryService {

    private final RepaymentStrategyRepository repository;

    public List<RepaymentStrategyView> getAll() {
        return repository.findAllProjected();
    }

    public Optional<RepaymentStrategyView> getById(Long id) {
        return repository.findProjectedById(id);
    }
}
