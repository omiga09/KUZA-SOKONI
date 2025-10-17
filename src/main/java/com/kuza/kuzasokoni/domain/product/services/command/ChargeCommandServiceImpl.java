package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.exception.ChargeNotFoundException;
import com.kuza.kuzasokoni.domain.product.mappers.ChargeCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ChargeCommandServiceImpl implements ChargeCommandService {

    private final ChargeRepository chargeRepository;
    private final ChargeCommandMapper mapper;

    @Override
    public Charge createCharge(ChargeCreateCommand cmd) {
        Charge charge = mapper.toEntity(cmd);
        charge.setIsPaid(false);
        charge.setRemainingAmount(charge.getAmount());
        return chargeRepository.save(charge);
    }

    @Override
    public Charge updatePaymentStatus(Long chargeId, BigDecimal paidAmount) {
        Charge charge = chargeRepository.findById(chargeId)
                .orElseThrow(() -> new ChargeNotFoundException(chargeId));

        charge.setPaidAmount(paidAmount);

        if (paidAmount != null && paidAmount.compareTo(charge.getAmount()) >= 0) {
            charge.setIsPaid(true);
            charge.setRemainingAmount(BigDecimal.ZERO);
        } else {
            charge.setIsPaid(false);
            BigDecimal remaining = charge.getAmount().subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO);
            charge.setRemainingAmount(remaining);
        }

        return chargeRepository.save(charge);
    }
}
