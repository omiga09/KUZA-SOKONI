package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.exception.ChargeNotFoundException;
import com.kuza.kuzasokoni.domain.product.mappers.ChargeCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import com.kuza.kuzasokoni.domain.product.services.query.ChargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ChargeCommandServiceImpl implements ChargeCommandService {

    private final ChargeRepository chargeRepository;
    private final ChargeCommandMapper mapper;
    private final ChargeQueryService chargeQueryService; // ← ongeza hii

    @Override
    public ChargeView createCharge(ChargeCreateCommand cmd) {
        Charge charge = mapper.toEntity(cmd);
        charge.setIsPaid(false);
        charge.setRemainingAmount(charge.getAmount());
        Charge saved = chargeRepository.save(charge);
        return chargeQueryService.getChargeById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Charge projection not found"));
    }

    @Override
    public ChargeView updatePaymentStatus(Long chargeId, BigDecimal paidAmount) {
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

        Charge updated = chargeRepository.save(charge);
        return chargeQueryService.getChargeById(updated.getId())
                .orElseThrow(() -> new RuntimeException("Charge projection not found"));
    }
}


