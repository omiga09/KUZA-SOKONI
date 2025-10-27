package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.RepaymentStrategyCommand;
import com.kuza.kuzasokoni.domain.product.entities.RepaymentStrategy;
import com.kuza.kuzasokoni.domain.product.repositories.RepaymentStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepaymentStrategyCommandServiceImpl implements RepaymentStrategyCommandService {

    private final RepaymentStrategyRepository repository;

    @Override
    public RepaymentStrategy create(RepaymentStrategyCommand command) {

        boolean exists = repository.existsByName(command.name());
        if (exists) {
            throw new IllegalArgumentException("Repayment strategy with name '" + command.name() + "' already exists.");
        }


        RepaymentStrategy strategy = RepaymentStrategy.builder()
                .name(command.name())
                .principal(command.principal())
                .interest(command.interest())
                .penalty(command.penalty())
                .fees(command.fees())
                .charges(command.charges())
                .build();

        return repository.save(strategy);
    }
}
