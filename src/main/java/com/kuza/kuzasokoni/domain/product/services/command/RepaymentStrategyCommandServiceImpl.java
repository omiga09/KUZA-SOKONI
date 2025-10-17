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

    public RepaymentStrategy create(RepaymentStrategyCommand command) {
        RepaymentStrategy strategy = new RepaymentStrategy();
        strategy.setName(command.name());
        strategy.setIncludePrincipal(command.includePrincipal());
        strategy.setIncludeInterest(command.includeInterest());
        strategy.setIncludePenalty(command.includePenalty());
        strategy.setIncludeFees(command.includeFees());
        strategy.setIncludeCharges(command.includeCharges());

        return repository.save(strategy);
    }

}
